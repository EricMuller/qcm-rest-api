package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.AccountRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.MpttCategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionTagRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagQuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionEntityUpdateMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionnaireQuestionEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.TagEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.UuidMapper;
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

import static java.util.Objects.nonNull;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional
public class QuestionPersistenceAdapter implements QuestionPersistencePort {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    private final QuestionEntityMapper questionMapper;

    private final QuestionEntityUpdateMapper questionUpdateMapper;

    private final TagQuestionRepository tagQuestionRepository;

    private final MpttCategoryRepository mpttCategoryRepository;

    private final UuidMapper uuidMapper;

    private final TagEntityMapper tagMapper;

    private final QuestionnaireQuestionEntityMapper questionnaireQuestionMapper;

    private final AccountRepository accountRepository;

    @Autowired
    public QuestionPersistenceAdapter(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository,
                                      QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                      QuestionEntityMapper questionMapper, QuestionEntityUpdateMapper questionUpdateMapper, TagQuestionRepository tagQuestionRepository,
                                      MpttCategoryRepository mpttCategoryRepository, UuidMapper uuidMapper, TagEntityMapper tagMapper, QuestionnaireQuestionEntityMapper questionnaireQuestionMapper,
                                      AccountRepository accountRepository) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.questionMapper = questionMapper;
        this.questionUpdateMapper = questionUpdateMapper;
        this.tagQuestionRepository = tagQuestionRepository;
        this.mpttCategoryRepository = mpttCategoryRepository;
        this.uuidMapper = uuidMapper;
        this.tagMapper = tagMapper;
        this.questionnaireQuestionMapper = questionnaireQuestionMapper;
        this.accountRepository = accountRepository;
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
    public Question updateQuestion(Question question, @NotNull String principal) {

        QuestionEntity questionEntity;
        MpttCategoryEntity mpttCategoryEntity = null;
        UUID categoryUuid = uuidMapper.getUuid(question.getMpttCategory());

        if (nonNull(categoryUuid)) {
            mpttCategoryEntity = mpttCategoryRepository.findByUuid(categoryUuid);
        }

        questionEntity = questionRepository.findByUuid(UUID.fromString(question.getId().toUuid())).orElse(null);
        questionEntity = questionUpdateMapper.questionToEntity(question, questionEntity);
        questionEntity.setResponses(questionUpdateMapper.responsesToEntities(question.getResponses(), questionEntity.getResponses()));


        questionEntity.setMpttCategory(mpttCategoryEntity);

        questionEntity = questionRepository.save(questionEntity);

        saveQuestionWithTags(questionEntity, question.getTags(), principal);

        return questionMapper.entityToQuestion(questionEntity);

    }

    @Override
    public Question saveQuestion(Question question, @NotNull String principal) {

        QuestionEntity questionEntity;
        MpttCategoryEntity mpttCategoryEntity = null;
        UUID categoryUuid = uuidMapper.getUuid(question.getMpttCategory());

        if (nonNull(categoryUuid)) {
            mpttCategoryEntity = mpttCategoryRepository.findByUuid(categoryUuid);
        }

        questionEntity = questionMapper.questionToEntity(question);

        Optional <AccountEntity> accountEntity = accountRepository.findByUuid(UUID.fromString(principal));
        if (accountEntity.isPresent()) {
            questionEntity.setOwner(accountEntity.get());
        }

        questionEntity.setMpttCategory(mpttCategoryEntity);

        questionEntity = questionRepository.save(questionEntity);

        saveQuestionWithTags(questionEntity, question.getTags(), principal);

        return questionMapper.entityToQuestion(questionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionWithTagsOnly> findAllByPage(String[] questionnaireIds, String[] tagIds, Pageable pageable, String principal) {

        var questionSpecificationBuilder = new QuestionEntity.SpecificationBuilder(principal);

        questionSpecificationBuilder.setQuestionnaireUuids(uuidMapper.toUUIDs(questionnaireIds));
        questionSpecificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagIds));

        return questionMapper.pageEntityToPageTagDto(questionRepository.findAll(questionSpecificationBuilder.build(), pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Page <Tag> findAllTagByPage(Pageable pageable, String principal) {

        return tagMapper.pageToModel(questionTagRepository.findAllTagByPrincipal(principal, pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <String> findAllStatusByPage(String principal, Pageable pageable) {
        return StreamSupport.stream(questionRepository.findAllStatusByCreatedBy(principal, pageable)
                        .spliterator(), false)
                .collect(Collectors.toList());
    }


    private QuestionEntity saveQuestionWithTags(QuestionEntity questionEntity, Iterable <QuestionTag> questionTags, String principal) {

        if (nonNull(questionEntity)) {
            questionEntity.getTags().clear();
            if (nonNull(questionTags)) {
                StreamSupport.stream(questionTags.spliterator(), false)
                        .forEach(questionTag -> {
                            TagQuestionEntity tagQuestionEntity = findTag(questionTag, principal);
                            if (nonNull(tagQuestionEntity)) {
                                var newQuestionTag = new QuestionTagBuilder()
                                        .setQuestion(questionEntity)
                                        .setTag(tagQuestionEntity)
                                        .build();
                                questionEntity.getTags().add(questionTagRepository.save(newQuestionTag));
                            }
                        });
            }
        }
        return questionEntity;
    }

    private TagQuestionEntity findTag(QuestionTag questionTag, String principal) {
        TagQuestionEntity tagQuestionEntity;
        if (nonNull(questionTag.getUuid())) {
            tagQuestionEntity = tagQuestionRepository.findByUuid(UUID.fromString(questionTag.getUuid())).orElse(null);
        } else {
            tagQuestionEntity = tagQuestionRepository.findByLibelle(questionTag.getLibelle(), principal);
            if (Objects.isNull(tagQuestionEntity)) {
                tagQuestionEntity = tagQuestionRepository.save(new TagQuestionEntity(questionTag.getLibelle()));
            }
        }
        return tagQuestionEntity;
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid) {

        return questionnaireQuestionMapper.questionnaireQuestionEntityToDomain(
                questionnaireQuestionRepository.findWithTagsAndResponseByQuestionnaireUuid(UUID.fromString(questionnaireUuid)));
    }

}
