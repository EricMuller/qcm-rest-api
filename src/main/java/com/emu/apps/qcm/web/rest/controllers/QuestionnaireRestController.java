package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.QuestionnaireTagService;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.jpa.repositories.specifications.questionnaire.QuestionnaireSpecification;
import com.emu.apps.qcm.web.rest.QuestionnaireRestApi;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.utils.DtoUtil;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireTagMapper;
import com.emu.apps.shared.web.rest.utils.ExceptionUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class QuestionnaireRestController implements QuestionnaireRestApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireService questionnairesService;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionnaireTagService questionnaireTagService;

    @Autowired
    private QuestionnaireTagMapper questionnaireTagMapper;

    @Autowired
    private QuestionnaireSpecification questionnaireSpecification;

    @Autowired
    private DtoUtil dtoUtil;

    @Override
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        questionnairesService.deleteById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @Override
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        Questionnaire questionnaire = questionnairesService.findOne(questionnaireDto.getId());

        questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaire, questionnaireDto));

        Iterable <QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        Questionnaire questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable <QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    public Page <QuestionDto> getQuestionsByByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable) {
        return questionMapper.pageQuestionResponseProjectionToDto(questionService.getQuestionsProjectionByQuestionnaireId(id, pageable));
    }

    @Override
    public Iterable <SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {
        final List <SuggestDto> suggestions = Lists.newArrayList();
        if (StringUtils.isNoneEmpty(queryText)) {
            questionnaireMapper.modelsToSuggestDtos(questionnairesService.findByTitleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

    @Override
    public Iterable <QuestionnaireDto> getQuestionnairesWithFilters(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException {

        FilterDto[] filterDtos = dtoUtil.stringToFilterDtos(filterString);

        return questionnaireMapper.pageToDto(questionnairesService.findAllByPage(questionnaireSpecification.getSpecifications(filterDtos, principal), pageable));
    }

    @Override
    public QuestionDto updateQuestionnaire(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, @RequestBody QuestionDto questionDto) {


        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, "Questionnaire Not found");
        Question question = questionService.findById(questionDto.getId()).orElse(null);
        ExceptionUtil.assertFound(question, "Question Not found");
        questionnairesService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, 0L));

        return questionDto;

    }


}