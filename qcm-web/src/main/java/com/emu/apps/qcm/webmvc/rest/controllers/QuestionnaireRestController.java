package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.QuestionnaireTagService;
import com.emu.apps.qcm.services.exceptions.ExceptionUtil;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.emu.apps.qcm.shared.security.PrincipalUtils;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.mappers.QuestionMapper;
import com.emu.apps.qcm.web.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.web.mappers.QuestionnaireTagMapper;
import com.emu.apps.qcm.webmvc.rest.QuestionnaireRestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@Profile("webmvc")
public class QuestionnaireRestController implements QuestionnaireRestApi {

    private final QuestionnaireService questionnairesService;

    private final QuestionnaireMapper questionnaireMapper;

    private final QuestionMapper questionMapper;

    private final QuestionService questionService;

    private final QuestionnaireTagService questionnaireTagService;

    private final QuestionnaireTagMapper questionnaireTagMapper;


    @Autowired
    public QuestionnaireRestController(QuestionnaireService questionnairesService, QuestionnaireMapper questionnaireMapper,
                                       QuestionMapper questionMapper, QuestionService questionService,
                                       QuestionnaireTagService questionnaireTagService, QuestionnaireTagMapper questionnaireTagMapper) {
        this.questionnairesService = questionnairesService;
        this.questionnaireMapper = questionnaireMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.questionnaireTagService = questionnaireTagService;
        this.questionnaireTagMapper = questionnaireTagMapper;
    }


    @Override
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        var questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertIsPresent(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        var questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertIsPresent(questionnaire, String.valueOf(id));
        questionnairesService.deleteById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @Override
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnairesService.findOne(questionnaireDto.getId());

        questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaire, questionnaireDto));

        var questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        var questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable <QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
//    public Page<QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable) {
    public Page <QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable) {
        return questionMapper.pageQuestionResponseProjectionToDto(questionService.getQuestionsProjectionByQuestionnaireId(id, pageable));

    }


    @Override
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                     Pageable pageable, Principal principal) {

        var specificationBuilder = new QuestionnaireSpecificationBuilder();

        specificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        specificationBuilder.setTagIds(tagIds);

        return questionnaireMapper.pageToDto(questionnairesService.findAllByPage(
                specificationBuilder.build(), pageable));
    }

    @Override
    public QuestionDto updateQuestionnaire(@PathVariable("id") long id, @RequestBody QuestionDto questionDto) {
        var questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertIsPresent(questionnaire, "Questionnaire Not found");
        var question = questionService.findById(questionDto.getId()).orElse(null);
        ExceptionUtil.assertIsPresent(question, "Question Not found");
        questionnairesService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, 0L));

        return questionMapper.modelToDto(question);

    }

}
