package com.emu.apps.qcm.web.reactive;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmReactiveApi.API_V1 + "/questions")
@Api(value = "questions-store",  tags = "Questions")
@SwaggerDefinition(tags = {
        @Tag(name = "Questions", description = "All operations ")
})
public interface QuestionReactiveApi {
    @ApiOperation(value = "Find all questions  by Page", responseContainer = "List", response = QuestionDto.class, nickname = "getQuestions")
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
    @GetMapping(produces = "application/json")
    @Timer
    Flux<Iterable<QuestionTagsDto>> getQuestions(@ApiParam(value = "Identifiant du tag") @RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                  @ApiParam(value = "Identifiant du questionnaire") @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                                  Pageable pageable, Principal principal)  ;

    @ApiOperation(value = "Find a question by ID", response = QuestionDto.class, nickname = "getQuestionById")
    @GetMapping(value = "/{id}")
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#id")
    Mono<QuestionDto> getQuestionById(@PathVariable("id") long id);

    @ApiOperation(value = "Update a question", response = Question.class, nickname = "updateQuestion")
    @PutMapping
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    Mono<QuestionDto> updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal);

    @ApiOperation(value = "Save a question", response = QuestionDto.class, nickname = "saveQuestion")
    @PostMapping
    @ResponseBody
    Mono<QuestionDto> saveQuestion(@RequestBody QuestionDto questionDto, Principal principal);

    @ApiOperation(value = "Delete a question by ID", response = ResponseEntity.class, nickname = "deleteQuestionById")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    Mono<ResponseEntity<Question>> deleteQuestionnaireById(@PathVariable("id") long id);


}
