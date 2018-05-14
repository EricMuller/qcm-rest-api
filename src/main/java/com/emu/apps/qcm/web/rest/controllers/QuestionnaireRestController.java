package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.QuestionnaireTagService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.repositories.specifications.questionnaire.QuestionnaireSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.exceptions.ExceptionUtil;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionnaireTagMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/questionnaires")
@Api(value = "questionnaire-store", description = "All operations ", tags = "Questionnaire")
public class QuestionnaireRestController {

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

    public static boolean isEmpty(final byte[] data) {
        return IntStream.range(0, data.length).parallel().allMatch(i -> data[i] == 0);
    }

    @ApiOperation(value = "Find a currentQuestionnaire by ID", response = QuestionnaireDto.class, nickname = "getQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findById(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        return questionnaireMapper.modelToDto(questionnaire);
    }

    @ApiOperation(value = "Delete a currentQuestionnaire by ID", response = ResponseEntity.class, nickname = "deleteQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        Questionnaire questionnaire = questionnairesService.findById(id);
        ExceptionUtil.assertFound(questionnaire, String.valueOf(id));
        questionnairesService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Save a currentQuestionnaire", response = QuestionnaireDto.class, nickname = "saveEpic")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto) {

        Questionnaire questionnaire = questionnairesService.saveQuestionnaire(questionnaireMapper.dtoToModel(questionnaireDto));

        Iterable<QuestionnaireTag> questionnaireTags = questionnaireTagMapper.dtosToModels(questionnaireDto.getQuestionnaireTags());

        questionnaire = questionnaireTagService.saveQuestionnaireTags(questionnaire.getId(), questionnaireTags);

        return questionnaireMapper.modelToDto(questionnaire);
    }

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
    public Iterable<QuestionnaireDto> getQuestionnairesWithFilters(@RequestParam(value = "filters", required = false) String filterString, Pageable pageable) {
        if (StringUtils.isNoneEmpty(filterString)) {
            byte[] bytes = Base64.getDecoder().decode(filterString);
            if (!isEmpty(bytes)) {
                try {
                    final FilterDto[] filterDtos = new ObjectMapper().readValue(bytes, FilterDto[].class);
                    if (ArrayUtils.isNotEmpty(filterDtos)) {
                        return questionnaireMapper.pageToDto(questionnairesService.findAllBySpecifications(questionnaireSpecification.getFilter(filterDtos), pageable));
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return questionnaireMapper.pageToDto(questionnairesService.findAllByPage(pageable));
    }

}