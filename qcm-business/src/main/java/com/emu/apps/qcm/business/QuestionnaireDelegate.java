package com.emu.apps.qcm.business;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.QuestionnaireTagService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.services.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.mappers.QuestionMapper;
import com.emu.apps.qcm.web.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.web.mappers.QuestionnaireTagMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class QuestionnaireDelegate {

    private final QuestionnaireService questionnairesService;

    private final QuestionnaireMapper questionnaireMapper;

    private final QuestionnaireTagService questionnaireTagService;

    private final QuestionnaireTagMapper questionnaireTagMapper;

    private final QuestionMapper questionMapper;

    private final QuestionService questionService;

    public QuestionnaireDelegate(QuestionnaireService questionnairesService, QuestionnaireMapper questionnaireMapper,
                                 QuestionMapper questionMapper, QuestionService questionService,
                                 QuestionnaireTagService questionnaireTagService, QuestionnaireTagMapper questionnaireTagMapper) {
        this.questionnairesService = questionnairesService;
        this.questionnaireMapper = questionnaireMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.questionnaireTagService = questionnaireTagService;
        this.questionnaireTagMapper = questionnaireTagMapper;
    }


    public QuestionnaireDto getQuestionnaireById(long id) {
        var questionnaire = questionnairesService.findOne(id);
        EntityExceptionUtil.assertIsPresent(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    public ResponseEntity <Questionnaire> deleteQuestionnaireById(long id) {
        var questionnaire = questionnairesService.findOne(id);
        EntityExceptionUtil.assertIsPresent(questionnaire, String.valueOf(id));
        questionnairesService.deleteById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    public QuestionnaireDto updateQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnairesService.findOne(questionnaireDto.getId());

        questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaire, questionnaireDto));

        var questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    public QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable <QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    public Page <QuestionDto> getQuestionsByQuestionnaireId(long id, Pageable pageable) {
        return questionMapper.pageQuestionResponseProjectionToDto(questionService.getQuestionsProjectionByQuestionnaireId(id, pageable));

    }

    public Page <QuestionnaireDto> getQuestionnaires(Long[] tagIds, Pageable pageable, Principal principal) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        specificationBuilder.setTagIds(tagIds);

        return questionnaireMapper.pageToDto(questionnairesService.findAllByPage(
                specificationBuilder.build(), pageable));
    }

    public QuestionDto updateQuestionnaire(long id, QuestionDto questionDto) {
        var questionnaire = questionnairesService.findOne(id);
        EntityExceptionUtil.assertIsPresent(questionnaire, "Questionnaire Not found");
        var question = questionService.findById(questionDto.getId()).orElse(null);
        EntityExceptionUtil.assertIsPresent(question, "Question Not found");
        questionnairesService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, 0L));
        return questionMapper.modelToDto(question);
    }


}
