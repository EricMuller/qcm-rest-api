package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.metrics.Timer;
import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.QuestionnaireTagService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.repositories.specifications.questionnaire.QuestionnaireSpecification;
import com.emu.apps.qcm.web.rest.QuestionnaireRestApi;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireTagMapper;
import com.emu.apps.qcm.web.rest.utils.ExceptionUtil;
import com.emu.apps.qcm.web.rest.utils.StringToFilter;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    private StringToFilter stringToFilter;

    @Override
    @ApiOperation(value = "Find a currentQuestionnaire by ID", response = QuestionnaireDto.class, nickname = "getQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    @ApiOperation(value = "Delete a currentQuestionnaire by ID", response = ResponseEntity.class, nickname = "deleteQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    public ResponseEntity<Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        questionnairesService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @ApiOperation(value = "Update a currentQuestionnaire", response = QuestionnaireDto.class, nickname = "updateQuestionnaire")
    @RequestMapping(method = {RequestMethod.PUT})
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        Questionnaire questionnaire = questionnairesService.findOne(questionnaireDto.getId());

        questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaire, questionnaireDto));

        Iterable<QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    @ApiOperation(value = "Save a currentQuestionnaire", response = QuestionnaireDto.class, nickname = "saveQuestionnaire")
    @RequestMapping(method = {RequestMethod.POST})
    @ResponseBody
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {

        Questionnaire questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable<QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags, principal);

        return questionnaireMapper.modelToDto(questionnaire);
    }

    @Override
    @ApiOperation(value = "Find all questions by QuestionnaireID", nickname = "getQuestionsProjectionByQuestionnaireId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/{id:[\\d]+}/questions", method = RequestMethod.GET)
    @ResponseBody
    public Page<QuestionDto> getQuestionsByByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable) {
        return questionMapper.pageQuestionResponseProjectionToDto(questionService.getQuestionsProjectionByQuestionnaireId(id, pageable));
    }

    @Override
    @ApiOperation(value = "Find suggestions ", responseContainer = "List", response = SuggestDto.class, nickname = "getSuggestions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/suggest", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @PreAuthorize("true")
    public Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {
        final List<SuggestDto> suggestions = Lists.newArrayList();
        if (StringUtils.isNoneEmpty(queryText)) {
            questionnaireMapper.modelsToSuggestDtos(questionnairesService.findByTitleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

    @Override
    @ApiOperation(value = "Find all questionnaires By Page", nickname = "getQuestionnaires")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filters", dataType = "String", paramType = "query", value = "base64 encoded string"),
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @Timer
    public Iterable<QuestionnaireDto> getQuestionnairesWithFilters(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException {

        FilterDto[] filterDtos = stringToFilter.getFilterDtos(filterString);

        return questionnaireMapper.pageToDto(questionnairesService.findAllByPage(questionnaireSpecification.getSpecifications(filterDtos, principal), pageable));
    }

    @Override
    @ApiOperation(value = "Add Question", response = QuestionnaireDto.class, nickname = "addQuestion")
    @RequestMapping(value = "/{id}/questions", method = RequestMethod.PUT)
    @ResponseBody
    public QuestionDto updateQuestionnaire(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, @RequestBody QuestionDto questionDto) {


        Questionnaire questionnaire = questionnairesService.findOne(id);
        ExceptionUtil.assertFound(questionnaire, "Questionnaire Not found");
        Question question = questionService.findOne(questionDto.getId());
        ExceptionUtil.assertFound(question, "Question Not found");
        questionnairesService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, 0L));

        return questionDto;

    }


}