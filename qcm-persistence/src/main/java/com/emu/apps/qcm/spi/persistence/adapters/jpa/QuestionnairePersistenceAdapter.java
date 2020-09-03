package com.emu.apps.qcm.spi.persistence.adapters.jpa;


import com.emu.apps.qcm.api.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Questionnaire;
import com.emu.apps.qcm.api.models.QuestionnaireQuestion;
import com.emu.apps.qcm.api.models.QuestionnaireTag;
import com.emu.apps.qcm.spi.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
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

    private CategoryRepository categoryRepository;

    private final QuestionnaireMapper questionnaireMapper;

    private UuidMapper uuidMapper;

    private TagRepository tagRepository;

    private QuestionnaireTagRepository questionnaireTagRepository;

    private PublishedMapper publishedMapper;

    private QuestionnaireQuestionMapper questionnaireQuestionMapper;

    public QuestionnairePersistenceAdapter(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository,
                                           QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                           CategoryRepository categoryRepository, QuestionnaireMapper questionnaireMapper,
                                           UuidMapper uuidMapper, TagRepository tagRepository,
                                           QuestionnaireTagRepository questionnaireTagRepository, PublishedMapper guestMapper, QuestionnaireQuestionMapper questionnaireQuestionMapper) {
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
    public Questionnaire saveQuestionnaire(final Questionnaire questionnaireDto, String principal) {

        CategoryEntity category = null;
        UUID uuid = uuidMapper.getUuid(questionnaireDto.getCategory());
        if (Objects.nonNull(uuid)) {
            category = categoryRepository.findByUuid(uuid);
        }

        QuestionnaireEntity questionnaire = null;
        if (StringUtils.isNotBlank(questionnaireDto.getUuid())) {
            questionnaire = questionnaireRepository.findByUuid(UUID.fromString(questionnaireDto.getUuid())).orElse(null);
        }

        if (Objects.nonNull(questionnaire)) {
            questionnaire = questionnaireMapper.dtoToModel(questionnaire, questionnaireDto);
            questionnaire.setCategory(category);
        } else {
            questionnaire = questionnaireMapper.dtoToModel(questionnaireDto);
            questionnaire.setCategory(category);
            questionnaire = questionnaireRepository.save(questionnaire);
        }

        saveQuestionnaireTags(questionnaire, questionnaireDto.getQuestionnaireTags(), principal);

        return questionnaireMapper.modelToDto(questionnaire);
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


    private QuestionnaireEntity saveQuestionnaireTags(QuestionnaireEntity questionnaire, Iterable <QuestionnaireTag> questionnaireTagDtos, String principal) {

        questionnaire.getQuestionnaireTags().clear();

        if (Objects.nonNull(questionnaireTagDtos)) {
            for (QuestionnaireTag questionnaireTagDto : questionnaireTagDtos) {
                Tag tag;
                if (Objects.nonNull(questionnaireTagDto.getUuid())) {
                    tag = tagRepository.findByUuid(UUID.fromString(questionnaireTagDto.getUuid()))
                            .orElse(null);
                } else {
                    tag = tagRepository.findByLibelle(questionnaireTagDto.getLibelle(), principal);
                    if (Objects.isNull(tag)) {
                        tag = tagRepository.save(new Tag(questionnaireTagDto.getLibelle(), true));
                    }
                }
                if (tag != null) {
                    QuestionnaireTagEntity newTag = new QuestionnaireTagBuilder()
                            .setQuestionnaire(questionnaire)
                            .setTag(tag)
                            .build();
                    questionnaire.getQuestionnaireTags().add(questionnaireTagRepository.save(newTag));
                }
            }
        }
        return questionnaire;

    }

    @Override
    public Question addQuestion(String uuid, Question questionDto, Optional <Integer> positionOpt) {

        var questionnaire = questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null);
        RaiseExceptionUtil.raiseIfNull(uuid, questionnaire, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE);

        if (Objects.nonNull(questionnaire) && Objects.nonNull(questionDto.getUuid())) {

            Integer position = positionOpt.isEmpty() ? questionnaire.getQuestionnaireQuestions().size() + 1 : positionOpt.get();
            var question = questionRepository.findByUuid(UUID.fromString(questionDto.getUuid())).orElse(null);
            if (Objects.nonNull(question)) {
                questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaire, question, position));
            }
        }

        return questionDto;
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
        return questionnaireRepository.findAllDistinctCategory_LibelleByPublishedTrue();
    }

    public Iterable <String> findPublishedTags() {

        return questionnaireTagRepository.findDistinctTagLibelleByDeletedFalseAndQuestionnaire_PublishedTrue();
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
