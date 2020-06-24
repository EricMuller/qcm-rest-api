package com.emu.apps.qcm.infrastructure.adapters.jpa;


import com.emu.apps.qcm.dtos.published.PublishedCategoryDto;
import com.emu.apps.qcm.dtos.published.PublishedTagDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.*;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.infrastructure.ports.QuestionnairePersistencePort;
import com.emu.apps.qcm.mappers.GuestMapper;
import com.emu.apps.qcm.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.mappers.UuidMapper;
import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.models.QuestionnaireTagDto;
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

    private GuestMapper guestMapper;

    public QuestionnairePersistenceAdapter(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository,
                                           QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                           CategoryRepository categoryRepository, QuestionnaireMapper questionnaireMapper,
                                           UuidMapper uuidMapper, TagRepository tagRepository,
                                           QuestionnaireTagRepository questionnaireTagRepository, GuestMapper guestMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.categoryRepository = categoryRepository;
        this.questionnaireMapper = questionnaireMapper;
        this.uuidMapper = uuidMapper;
        this.tagRepository = tagRepository;
        this.questionnaireTagRepository = questionnaireTagRepository;
        this.guestMapper = guestMapper;

    }

    @Override
    @Transactional()
    public void deleteByUuid(String uuid) {
        questionnaireRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionnaireDto findByUuid(String uuid) {

        return questionnaireMapper.modelToDto(questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null));
    }


    @Override
    public QuestionnaireDto saveQuestionnaire(final QuestionnaireDto questionnaireDto, String principal) {

        Category category = null;
        UUID uuid = uuidMapper.getUuid(questionnaireDto.getCategory());
        if (Objects.nonNull(uuid)) {
            category = categoryRepository.findByUuid(uuid);
        }

        Questionnaire questionnaire;
        if (StringUtils.isNotBlank(questionnaireDto.getUuid())) {
            questionnaire = questionnaireRepository.findByUuid(UUID.fromString(questionnaireDto.getUuid())).get();
            questionnaire = questionnaireMapper.dtoToModel(questionnaire, questionnaireDto);
        } else {
            questionnaire = questionnaireMapper.dtoToModel(questionnaireDto);
            questionnaireRepository.save(questionnaire);
        }
        questionnaire.setCategory(category);

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
    public Page <QuestionnaireDto> findAllByPage(String[] tagUuid, String principal, Pageable pageable) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPrincipal(principal);
        specificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagUuid));

        return questionnaireMapper.pageToDto(questionnaireRepository.findAll(specificationBuilder.build(), pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionnaireDto> findAllPublicByPage(String[] tagUuid, String principal, Pageable pageable) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPrincipal(principal);
        specificationBuilder.setPublished(Boolean.TRUE);
        specificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagUuid));

        return questionnaireMapper.pageToDto(questionnaireRepository.findAll(specificationBuilder.build(), pageable));

    }

    private Questionnaire saveQuestionnaireTags(Questionnaire questionnaire, Iterable <QuestionnaireTagDto> questionnaireTagDtos, String principal) {

        questionnaire.getQuestionnaireTags().clear();

        if (Objects.nonNull(questionnaireTagDtos)) {
            for (QuestionnaireTagDto questionnaireTagDto : questionnaireTagDtos) {
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
                    QuestionnaireTag newTag = new QuestionnaireTagBuilder()
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
    public QuestionDto addQuestion(String uuid, QuestionDto questionDto, Optional <Long> positionOpt) {

        var questionnaire = questionnaireRepository.findByUuid(UUID.fromString(uuid)).orElse(null);
        RaiseExceptionUtil.raiseIfNull(uuid, questionnaire, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE);

        Long position = positionOpt.isEmpty() ? questionnaire.getQuestionnaireQuestions().size() + 1L : positionOpt.get();

        if (Objects.nonNull(questionDto.getUuid())) {
            var question = questionRepository.findByUuid(UUID.fromString(questionDto.getUuid())).orElse(null);
            if (Objects.nonNull(question)) {
                questionnaireQuestionRepository.save(new QuestionnaireQuestion(questionnaire, question, position));
            }
        }

        return questionDto;
    }

    @Override
    public Iterable <PublishedCategoryDto> getPublicCategories() {
        return guestMapper.categoriesToDtos(questionnaireRepository.getAllPublicCategories());
    }

    public Iterable <PublishedTagDto> getPublicTags() {
        return guestMapper.tagsToDtos(questionnaireTagRepository.getPublicTags());
    }

    @Override
    public void deleteQuestion(String questionnaireUuid, String questionUuid) {

        QuestionnaireQuestion questionnaireQuestion = questionnaireQuestionRepository.findByQuestionUuid(UUID.fromString(questionnaireUuid), UUID.fromString(questionUuid));
        questionnaireQuestionRepository.delete(questionnaireQuestion);
    }
}
