package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionEntityUpdateMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionnaireQuestionEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.TagQuestionEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.UuidMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.AccountRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.MpttCategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionTagRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

    private final QuestionEntityMapper questionEntityMapper;

    private final QuestionEntityUpdateMapper questionEntityUpdateMapper;

    private final TagQuestionRepository tagQuestionRepository;

    private final MpttCategoryRepository mpttCategoryRepository;

    private final UuidMapper uuidMapper;

    private final TagQuestionEntityMapper tagMapper;

    private final QuestionnaireQuestionEntityMapper questionnaireQuestionEntityMapper;

    private final AccountRepository accountRepository;

    @Autowired
    public QuestionPersistenceAdapter(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository,
                                      QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                      QuestionEntityMapper questionEntityMapper, QuestionEntityUpdateMapper questionEntityUpdateMapper, TagQuestionRepository tagQuestionRepository,
                                      MpttCategoryRepository mpttCategoryRepository, UuidMapper uuidMapper, TagQuestionEntityMapper tagMapper, QuestionnaireQuestionEntityMapper questionnaireQuestionEntityMapper,
                                      AccountRepository accountRepository) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.questionEntityMapper = questionEntityMapper;
        this.questionEntityUpdateMapper = questionEntityUpdateMapper;
        this.tagQuestionRepository = tagQuestionRepository;
        this.mpttCategoryRepository = mpttCategoryRepository;
        this.uuidMapper = uuidMapper;
        this.tagMapper = tagMapper;
        this.questionnaireQuestionEntityMapper = questionnaireQuestionEntityMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional <Question> findByUuid(String uuid) {
        return Optional.ofNullable(questionEntityMapper.entityToQuestion(questionRepository.findByUuid(UUID.fromString(uuid)).orElse(null)));

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
        questionEntity = questionEntityUpdateMapper.questionToEntity(question, questionEntity);
        questionEntity.setResponses(questionEntityUpdateMapper.responsesToEntities(question.getResponses(), questionEntity.getResponses()));


        questionEntity.setMpttCategory(mpttCategoryEntity);

        questionEntity = questionRepository.save(questionEntity);

        saveQuestionWithTags(questionEntity, question.getTags(), principal);

        return questionEntityMapper.entityToQuestion(questionEntity);

    }

    @Override
    public Question saveQuestion(Question question, @NotNull String principal) {


        QuestionEntity questionEntity;
        MpttCategoryEntity mpttCategoryEntity = null;
        UUID categoryUuid = uuidMapper.getUuid(question.getMpttCategory());

        if (nonNull(categoryUuid)) {
            mpttCategoryEntity = mpttCategoryRepository.findByUuid(categoryUuid);
        }

        questionEntity = questionEntityMapper.questionToEntity(question);

        Optional <AccountEntity> accountEntity = accountRepository.findByUuid(UUID.fromString(principal));
        if (accountEntity.isPresent()) {
            questionEntity.setOwner(accountEntity.get());
        }

        questionEntity.setMpttCategory(mpttCategoryEntity);

        questionEntity = questionRepository.save(questionEntity);

        saveQuestionWithTags(questionEntity, question.getTags(), principal);

        return questionEntityMapper.entityToQuestion(questionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Tag> findAllTagByPage(Pageable pageable, String principal) {

        return tagMapper.pageToModel(questionTagRepository.findAllTagByPrincipal(principal, pageable));

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

        return questionnaireQuestionEntityMapper.questionnaireQuestionEntityToDomain(
                questionnaireQuestionRepository.findWithTagsAndResponseByQuestionnaireUuid(UUID.fromString(questionnaireUuid)));
    }

}
