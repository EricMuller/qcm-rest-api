package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionTagRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagRepository;
import com.emu.apps.qcm.infra.persistence.mappers.QuestionMapper;
import com.emu.apps.qcm.infra.persistence.mappers.QuestionnaireQuestionMapper;
import com.emu.apps.qcm.infra.persistence.mappers.TagMapper;
import com.emu.apps.qcm.infra.persistence.mappers.UuidMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional
public class QuestionPersistenceAdapter implements QuestionPersistencePort {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    private final QuestionMapper questionMapper;

    private final TagRepository tagRepository;

    private final CategoryRepository categoryRepository;

    private final UuidMapper uuidMapper;

    private final TagMapper tagMapper;

    private final QuestionnaireQuestionMapper questionnaireQuestionMapper;

    @Autowired
    public QuestionPersistenceAdapter(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository,
                                      QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                      QuestionMapper questionMapper, TagRepository tagRepository,
                                      CategoryRepository categoryRepository, UuidMapper uuidMapper, TagMapper tagMapper, QuestionnaireQuestionMapper questionnaireQuestionMapper) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.questionMapper = questionMapper;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.uuidMapper = uuidMapper;
        this.tagMapper = tagMapper;
        this.questionnaireQuestionMapper = questionnaireQuestionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional <Question> findByUuid(String uuid) {
        return Optional.ofNullable(questionMapper.entityToQuestion(questionRepository.findByUuid(UUID.fromString(uuid)).orElse(null)));

    }


    @Override
    public void deleteByUuid(String uuid) {
        questionRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    public Question saveQuestion(Question question, @NotNull String principal) {

        QuestionEntity questionEntity;
        CategoryEntity categoryEntity = null;
        UUID uuid = uuidMapper.getUuid(question.getCategory());

        if (Objects.nonNull(uuid)) {
            categoryEntity = categoryRepository.findByUuid(uuid);
        }

        if (StringUtils.isNotBlank(question.getUuid())) {
            questionEntity = questionRepository.findByUuid(UUID.fromString(question.getUuid())).orElse(null);
            questionEntity = questionMapper.dtoToModel(questionEntity, question);
        } else {
            questionEntity = questionMapper.dtoToModel(question);
        }

        questionEntity.setCategory(categoryEntity);

        questionEntity = questionRepository.save(questionEntity);

        saveQuestionWithTags(questionEntity, question.getQuestionTags(), principal);

        return questionMapper.entityToQuestion(questionEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionTags> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal) {

        var questionSpecificationBuilder = new QuestionEntity.SpecificationBuilder(principal);

        questionSpecificationBuilder.setQuestionnaireUuids(uuidMapper.toUUIDs(questionnaireUuids));
        questionSpecificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagUuids));

        return questionMapper.pageEntityToPageTagDto(questionRepository.findAll(questionSpecificationBuilder.build(), pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <Tag> findAllTagByPage(Pageable pageable, String principal) {

        return tagMapper.pageToDto(questionTagRepository.findAllTagByPrincipal(principal, pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <String> findAllStatusByPage(String principal, Pageable pageable) {
        return StreamSupport.stream(questionRepository.findAllStatusByCreatedBy(principal, pageable)
                .spliterator(), false)
                .collect(Collectors.toList());
    }


    private QuestionEntity saveQuestionWithTags(QuestionEntity questionEntity, Iterable <QuestionTag> questionTags, String principal) {

        if (Objects.nonNull(questionEntity)) {
            questionEntity.getQuestionTags().clear();
            if (Objects.nonNull(questionTags)) {
                StreamSupport.stream(questionTags.spliterator(), false)
                        .forEach(questionTag -> {
                            TagEntity tagEntity = findTag(questionTag, principal);
                            if (Objects.nonNull(tagEntity)) {
                                var newQuestionTag = new QuestionTagBuilder()
                                        .setQuestion(questionEntity)
                                        .setTag(tagEntity)
                                        .build();
                                questionEntity.getQuestionTags().add(questionTagRepository.save(newQuestionTag));
                            }
                        });
            }
        }
        return questionEntity;
    }

    private TagEntity findTag(QuestionTag questionTag, String principal) {
        TagEntity tagEntity;
        if (Objects.nonNull(questionTag.getUuid())) {
            tagEntity = tagRepository.findByUuid(UUID.fromString(questionTag.getUuid())).orElse(null);
        } else {
            tagEntity = tagRepository.findByLibelle(questionTag.getLibelle(), principal);
            if (Objects.isNull(tagEntity)) {
                tagEntity = tagRepository.save(new TagEntity(questionTag.getLibelle(), true));
            }
        }
        return tagEntity;
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid) {

        return questionnaireQuestionMapper.questionnaireQuestionEntityToDto(
                questionnaireQuestionRepository.findWithTagsAndResponseByQuestionnaireUuid(UUID.fromString(questionnaireUuid)));
    }

}
