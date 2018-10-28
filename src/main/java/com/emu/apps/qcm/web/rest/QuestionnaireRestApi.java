package com.emu.apps.qcm.web.rest;

import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RequestMapping(QcmVersion.API_V1 +"/questionnaires")
@Api(value = "questionnaire-store", description = "All operations ", tags = "Questionnaire")
public interface QuestionnaireRestApi {
    @ApiOperation(value = "Find a currentQuestionnaire by ID", response = QuestionnaireDto.class, nickname = "getQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id);

    @ApiOperation(value = "Delete a currentQuestionnaire by ID", response = ResponseEntity.class, nickname = "deleteQuestionnaireById")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    ResponseEntity<Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id);

    @ApiOperation(value = "Update a currentQuestionnaire", response = QuestionnaireDto.class, nickname = "updateQuestionnaire")
    @RequestMapping(method = {RequestMethod.PUT})
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @ApiOperation(value = "Save a currentQuestionnaire", response = QuestionnaireDto.class, nickname = "saveQuestionnaire")
    @RequestMapping(method = {RequestMethod.POST})
    @ResponseBody
    QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @ApiOperation(value = "Find all questions by QuestionnaireID", nickname = "getQuestionsProjectionByQuestionnaireId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/{id:[\\d]+}/questions", method = RequestMethod.GET)
    @ResponseBody
    Page<QuestionDto> getQuestionsByByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable);

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
    Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText);

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
    Iterable<QuestionnaireDto> getQuestionnairesWithFilters(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException;

    @ApiOperation(value = "Add Question", response = QuestionnaireDto.class, nickname = "addQuestion")
    @RequestMapping(value = "/{id}/questions", method = RequestMethod.PUT)
    @ResponseBody
    QuestionDto updateQuestionnaire(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, @RequestBody QuestionDto questionDto);
}
