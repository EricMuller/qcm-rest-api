package com.emu.apps.qcm.spi.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.aggregates.Question;
import com.emu.apps.qcm.aggregates.Questionnaire;
import com.emu.apps.qcm.aggregates.QuestionnaireQuestion;
import com.emu.apps.qcm.aggregates.QuestionnaireTag;
import com.emu.apps.qcm.spi.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.*;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.spi.persistence.mappers.PublishedMapper;
import com.emu.apps.qcm.spi.persistence.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.spi.persistence.mappers.QuestionnaireQuestionMapper;
import com.emu.apps.qcm.spi.persistence.mappers.UuidMapper;
import org.apache.commons.lang3.StringUtils;
import org.javers.core.Javers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class QuestionnairePersistenceAdapter implements QuestionnairePersistencePort {

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionRepository questionRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    private final CategoryRepository categoryRepository;

    private final QuestionnaireMapper questionnaireMapper;

    private final UuidMapper uuidMapper;

    private final TagRepository tagRepository;

    private final QuestionnaireTagRepository questionnaireTagRepository;

    private final PublishedMapper publishedMapper;

    private final QuestionnaireQuestionMapper questionnaireQuestionMapper;

    private final Javers javers;

    public QuestionnairePersistenceAdapter(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository,
                                           QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                           CategoryRepository categoryRepository, QuestionnaireMapper questionnaireMapper,
                                           UuidMapper uuidMapper, TagRepository tagRepository,
                                           QuestionnaireTagRepository questionnaireTagRepository, PublishedMapper guestMapper, QuestionnaireQuestionMapper questionnaireQuestionMapper, Javers javers) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.categoryRepository = categoryRepository;
        this.questionnaireMapper = questionnaireMapper;
        this.uuidMapper = uuidMapper;
        this.tagRepository = tagRepository;
        this.questionnaireTagRepository = questionnaireTagRepository;
        this.publishedMapper = guestMapper;

        this.questionnaireQuestionMapper = questionnaireQuestionMapper;
        this.javers = javers;
    }

    @Override
    @Transactional()
    public void deleteByUuid(String uuid) {
        questionnaireRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public Questionnaire findByUuid(String uuid) {

        return questionnaireMapper.modelToDto(questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null));
    }


    @Override
    public Questionnaire saveQuestionnaire(final Questionnaire questionnaire, String principal) {

        CategoryEntity category = null;
        UUID uuid = uuidMapper.getUuid(questionnaire.getCategory());
        if (Objects.nonNull(uuid)) {
            category = categoryRepository.findByUuid(uuid);
        }

        QuestionnaireEntity questionnaireEntity = null;
        if (StringUtils.isNotBlank(questionnaire.getUuid())) {
            questionnaireEntity = questionnaireRepository.findByUuid(UUID.fromString(questionnaire.getUuid())).orElse(null);
        }

        if (Objects.nonNull(questionnaireEntity)) {
            questionnaireEntity = questionnaireMapper.dtoToModel(questionnaireEntity, questionnaire);
            questionnaireEntity.setCategory(category);
        } else {
            questionnaireEntity = questionnaireMapper.dtoToModel(questionnaire);
            questionnaireEntity.setCategory(category);
            // flush for datemodification issue with javers
            questionnaireEntity = questionnaireRepository.saveAndFlush(questionnaireEntity);
        }

        saveQuestionnaireTags(questionnaireEntity, questionnaire.getQuestionnaireTags(), principal);

        javers.commit(principal, questionnaireEntity);

        return questionnaireMapper.modelToDto(questionnaireEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <QuestionnaireProjection> findByTitleContaining(String title) {
        return questionnaireRepository.findByTitleContaining(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Questionnaire> findAllByPage(String[] tagUuid, String principal, Pageable pageable) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();
        specificationBuilder.setPrincipal(principal);
        specificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagUuid));

        return questionnaireMapper.pageToDto(questionnaireRepository.findAll(specificationBuilder.build(), pageable));
    }


    private QuestionnaireEntity saveQuestionnaireTags(QuestionnaireEntity questionnaireEntity, Iterable <QuestionnaireTag> questionnaireTagDtos, String principal) {

        questionnaireEntity.getQuestionnaireTags().clear();

        if (Objects.nonNull(questionnaireTagDtos)) {
            for (QuestionnaireTag questionnaireTagDto : questionnaireTagDtos) {
                TagEntity tag;
                if (Objects.nonNull(questionnaireTagDto.getUuid())) {
                    tag = tagRepository.findByUuid(UUID.fromString(questionnaireTagDto.getUuid()))
                            .orElse(null);
                } else {
                    tag = tagRepository.findByLibelle(questionnaireTagDto.getLibelle(), principal);
                    if (Objects.isNull(tag)) {
                        tag = tagRepository.save(new TagEntity(questionnaireTagDto.getLibelle(), true));
                    }
                }
                if (tag != null) {
                    QuestionnaireTagEntity newTag = new QuestionnaireTagBuilder()
                            .setQuestionnaire(questionnaireEntity)
                            .setTag(tag)
                            .build();
                    questionnaireEntity.getQuestionnaireTags().add(questionnaireTagRepository.save(newTag));
                }
            }
        }
        return questionnaireEntity;

    }

    @Override
    public Question addQuestion(String uuid, Question question, Optional <Integer> positionOpt, String principal) {

        var questionnaireEntity = questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null);
        RaiseExceptionUtil.raiseIfNull(uuid, questionnaireEntity, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE);

        if (Objects.nonNull(questionnaireEntity) && Objects.nonNull(question.getUuid())) {

            Integer position = positionOpt.isEmpty() ? questionnaireEntity.getQuestionnaireQuestions().size() + 1 : positionOpt.get();
            var questionEntity = questionRepository.findByUuid(UUID.fromString(question.getUuid())).orElse(null);
            if (Objects.nonNull(questionEntity)) {
                questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity, position));
                javers.commit(principal, questionnaireEntity);
            }
        }

        return question;
    }


    @Override
    @Transactional(readOnly = true)
    public PublishedQuestionnaireDto findOnePublishedByUuid(String uuid) {

        QuestionnaireEntity questionnaire = questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null);

        RaiseExceptionUtil.raiseIfNull(uuid, questionnaire, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE);

        return publishedMapper.questionnaireToPublishedQuestionnaireDto(questionnaire);

    }

    @Override
    @Transactional(readOnly = true)
    public Page <PublishedQuestionnaireDto> findAllPublishedByPage(Pageable pageable) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPublished(Boolean.TRUE);

        return publishedMapper.pageQuestionnaireToPublishedQuestionnaireDto(questionnaireRepository.findAll(specificationBuilder.build(), pageable));

    }

    @Override
    public Iterable <String> findPublishedCategories() {
        return questionnaireRepository.findAllDistinctCategoryLibelleByPublishedTrue();
    }

    public Iterable <String> findPublishedTags() {

        return questionnaireTagRepository.findDistinctTagLibelleByDeletedFalseAndQuestionnairePublishedTrue();
    }

    @Override
    public void deleteQuestion(String questionnaireUuid, String questionUuid) {

        QuestionnaireQuestionEntity questionnaireQuestion = questionnaireQuestionRepository.findByQuestionUuid(UUID.fromString(questionnaireUuid), UUID.fromString(questionUuid));
        questionnaireQuestionRepository.delete(questionnaireQuestion);
    }

    @Transactional(readOnly = true)
    public Page <QuestionnaireQuestion> getQuestionsProjectionByQuestionnaireUuid(String questionnaireUuid, Pageable pageable) {
        return questionnaireQuestionMapper.pageQuestionResponseProjectionToDto(questionnaireQuestionRepository.findQuestionsByQuestionnaireUuiId(UUID.fromString(questionnaireUuid), pageable));
    }
}
