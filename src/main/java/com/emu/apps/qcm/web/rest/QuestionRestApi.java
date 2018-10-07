package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.metrics.Timer;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@RequestMapping(QcmVersion.API_V + "/questions")
@Api(value = "questions-store", description = "All operations ", tags = "Question")
public interface QuestionRestApi {
    @ApiOperation(value = "Find all questions  by Page", responseContainer = "List", response = QuestionDto.class, nickname = "getTagsByPAge")
    @ApiImplicitParams({
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
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @Timer
    Iterable<QuestionTagsDto> getQuestionsWithFilters(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException;

    @ApiOperation(value = "Find a question by ID", response = QuestionDto.class, nickname = "getQuestionById")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#id")
    QuestionDto getQuestionById(@PathVariable("id") long id);

    @ApiOperation(value = "Update a question", response = Question.class, nickname = "updateQuestion")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal);

    @ApiOperation(value = "Save a question", response = QuestionDto.class, nickname = "saveQuestion")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal);

    @ApiOperation(value = "Delete a question by ID", response = ResponseEntity.class, nickname = "deleteQuestionById")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    ResponseEntity<Question> deleteQuestionnaireById(@PathVariable("id") long id);
}
