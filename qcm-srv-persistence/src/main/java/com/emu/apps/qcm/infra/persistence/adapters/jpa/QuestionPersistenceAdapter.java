package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.QuestionTag;
import com.emu.apps.qcm.domain.models.Tag;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionTagRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagRepository;
import com.emu.apps.qcm.infra.persistence.mappers.QuestionMapper;
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

    @Autowired
    public QuestionPersistenceAdapter(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository,
                                      QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                      QuestionMapper questionMapper, TagRepository tagRepository,
                                      CategoryRepository categoryRepository, UuidMapper uuidMapper, TagMapper tagMapper) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.questionMapper = questionMapper;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.uuidMapper = uuidMapper;
        this.tagMapper = tagMapper;
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
    public Question saveQuestion(Question questionDto, @NotNull String principal) {

        QuestionEntity question;
        CategoryEntity category = null;
        UUID uuid = uuidMapper.getUuid(questionDto.getCategory());

        if (Objects.nonNull(uuid)) {
            category = categoryRepository.findByUuid(uuid);
        }

        if (StringUtils.isNotBlank(questionDto.getUuid())) {
            question = questionRepository.findByUuid(UUID.fromString(questionDto.getUuid())).orElse(null);
            question = questionMapper.dtoToModel(question, questionDto);
        } else {
            question = questionMapper.dtoToModel(questionDto);
        }

        question.setCategory(category);

        question = questionRepository.save(question);

        saveQuestionWithTags(question, questionDto.getQuestionTags(), principal);

        return questionMapper.entityToQuestion(question);

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
    public Iterable <String> findAllStatusByPage( String principal, Pageable pageable){
       return  StreamSupport.stream(questionRepository.findAllStatusByCreatedBy(principal,pageable)
                         .spliterator(), false)
                .map(status -> status.name())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <QuestionnaireQuestionEntity> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid) {

        return questionnaireQuestionRepository.findAllWithTagsAndResponseByQuestionnaireUuid(UUID.fromString(questionnaireUuid));

    }


    private QuestionEntity saveQuestionWithTags(QuestionEntity question, Iterable <QuestionTag> questionTags, String principal) {

        if (Objects.nonNull(question)) {
            question.getQuestionTags().clear();
            if (Objects.nonNull(questionTags)) {
                StreamSupport.stream(questionTags.spliterator(), false)
                        .forEach(questionTag -> {
                            TagEntity tag = findTag(questionTag, principal);
                            if (Objects.nonNull(tag)) {
                                var newQuestionTag = new QuestionTagBuilder()
                                        .setQuestion(question)
                                        .setTag(tag)
                                        .build();
                                question.getQuestionTags().add(questionTagRepository.save(newQuestionTag));
                            }
                        });
            }
        }
        return question;
    }

    private TagEntity findTag(QuestionTag questionTag, String principal) {
        TagEntity tag;
        if (Objects.nonNull(questionTag.getUuid())) {
            tag = tagRepository.findByUuid(UUID.fromString(questionTag.getUuid())).orElse(null);
        } else {
            tag = tagRepository.findByLibelle(questionTag.getLibelle(), principal);
            if (Objects.isNull(tag)) {
                tag = tagRepository.save(new TagEntity(questionTag.getLibelle(), true));
            }
        }
        return tag;
    }


}
