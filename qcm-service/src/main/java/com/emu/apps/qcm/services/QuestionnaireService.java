package com.emu.apps.qcm.services;

import com.emu.apps.qcm.domain.QuestionDOService;
import com.emu.apps.qcm.domain.QuestionnaireDOService;
import com.emu.apps.qcm.domain.QuestionnaireTagDOService;
import com.emu.apps.qcm.domain.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.domain.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.domain.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.emu.apps.qcm.mappers.QuestionMapper;
import com.emu.apps.qcm.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.mappers.QuestionnaireTagMapper;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.domain.exceptions.EntityExceptionUtil.assertIsPresent;

/**
 * Questionnaire Business Delegate
 * <p>
 * <ul>
 * <li>create a new questionnaire</li>
 * <li>delete a questionnaire</li>
 * <li>update a questionnaire</li>
 * <li>add a question to a questionnaire</li>
 * </ul>
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class QuestionnaireService {

    private final QuestionnaireDOService questionnaireDOService;

    private final QuestionnaireMapper questionnaireMapper;

    private final QuestionnaireTagDOService questionnaireTagDOService;

    private final QuestionnaireTagMapper questionnaireTagMapper;

    private final QuestionMapper questionMapper;

    private final QuestionDOService questionDOService;


    public QuestionnaireService(QuestionnaireDOService questionnaireDOService, QuestionnaireMapper questionnaireMapper, QuestionnaireTagDOService questionnaireTagDOService, QuestionnaireTagMapper questionnaireTagMapper, QuestionMapper questionMapper, QuestionDOService questionDOService) {
        this.questionnaireDOService = questionnaireDOService;
        this.questionnaireMapper = questionnaireMapper;
        this.questionnaireTagDOService = questionnaireTagDOService;
        this.questionnaireTagMapper = questionnaireTagMapper;
        this.questionMapper = questionMapper;
        this.questionDOService = questionDOService;
    }

    /**
     * Find a Questionnaire with technical identifier
     *
     * @param id
     * @return the questionnaire
     */
    public QuestionnaireDto getQuestionnaireById(long id) {
        var questionnaire = questionnaireDOService.findOne(id);
        assertIsPresent(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    /**
     * Delete a Questionnaire with technical identifier
     *
     * @param id
     * @return
     */
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(long id) {
        var questionnaire = questionnaireDOService.findOne(id);
        assertIsPresent(questionnaire, String.valueOf(id));
        questionnaireDOService.deleteById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update a existing questionnaire
     *
     * @param questionnaireDto the questionnaire DTO
     * @return the updated questionnaire
     */
    public QuestionnaireDto updateQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnaireDOService.findOne(questionnaireDto.getId());

        questionnaire = questionnaireDOService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaire, questionnaireDto));

        var questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagDOService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    /**
     * Create a  new Questionnaire
     *
     * @param questionnaireDto the questionnaire DTO
     * @return the created questionnaire
     */
    public QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnaireDOService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable <QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagDOService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    public Page <QuestionDto> getQuestionsByQuestionnaireId(long id, Pageable pageable) {
        return questionMapper.pageQuestionResponseProjectionToDto(questionDOService.getQuestionsProjectionByQuestionnaireId(id, pageable));
    }

    /**
     * find  a list of questionnaires
     *
     * @param tagIds    technical tag identifier list
     * @param pageable
     * @param principal
     * @return a list of questionnaires with the specified tag
     */
    public Page <QuestionnaireDto> getQuestionnaires(Long[] tagIds, Pageable pageable, Principal principal) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        specificationBuilder.setTagIds(tagIds);

        return questionnaireMapper.pageToDto(questionnaireDOService.findAllByPage(specificationBuilder.build(), pageable));
    }

    /**
     * Add  question to a questionnaire
     *
     * @param id
     * @param questionDto the question DTO
     * @return
     */
    public QuestionDto addQuestion(long id, QuestionDto questionDto, Long position) {
        var questionnaire = questionnaireDOService.findOne(id);
        if (Objects.isNull(position)) {
            position = questionnaire.getQuestionnaireQuestions().size() + 1L;
        }
        var question = questionDOService.findById(questionDto.getId());

        if (question.isPresent()) {
            var questionnaireQuestion = new QuestionnaireQuestion(questionnaire, question.get(), position);

            questionnaireQuestion = questionnaireDOService.saveQuestionnaireQuestion(questionnaireQuestion);

            questionnaire.getQuestionnaireQuestions().add(questionnaireQuestion);
        }
        return questionMapper.modelToDto(question.get());
    }

    /**
     * Add  questions to a questionnaire
     *
     * @param questionnaireId
     * @param questionDtos
     */
    public List <QuestionDto> addQuestions(long questionnaireId, Collection <QuestionDto> questionDtos) {

        AtomicLong atomicLong = new AtomicLong(0);
        return questionDtos
                .stream()
                .map(questionDto -> addQuestion(questionnaireId, questionDto, atomicLong.incrementAndGet()))
                .collect(Collectors.toList());
    }

}
