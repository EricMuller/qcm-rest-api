package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.application.QuestionCatalog;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.rest.controllers.secured.command.QuestionStatus;
import com.emu.apps.qcm.rest.controllers.secured.hal.QuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.secured.hal.SearchQuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.secured.hal.TagModelAssembler;
import com.emu.apps.qcm.rest.controllers.secured.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.secured.resources.MessageResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.SearchQuestionResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.TagResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.validators.ValidUuid;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.emu.apps.qcm.rest.config.cache.CacheName.Names.QUESTION;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.qcm.rest.controllers.secured.resources.MessageResource.ERROR;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTION;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.EntityModel.of;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping(value = PROTECTED_API + QUESTIONS, produces = {APPLICATION_JSON_VALUE, HAL_JSON_VALUE})
@io.swagger.v3.oas.annotations.tags.Tag(name = "Question")
@Validated
public class QuestionRestController {

    private final QuestionCatalog questionCatalog;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    private final SearchQuestionModelAssembler searchQuestionModelAssembler;

    private final QuestionModelAssembler questionModelAssembler;

    private final TagModelAssembler tagModelAssembler;

    public QuestionRestController(QuestionCatalog questionCatalog, QuestionnaireResourceMapper questionnaireResourceMapper,
                                  SearchQuestionModelAssembler searchQuestionModelAssembler,
                                  QuestionModelAssembler questionModelAssembler, TagModelAssembler tagModelAssembler) {
        this.questionCatalog = questionCatalog;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
        this.searchQuestionModelAssembler = searchQuestionModelAssembler;
        this.questionModelAssembler = questionModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @PageableAsQueryParam
    @Operation(summary = "Search Question", description = "", tags = {"yourObject"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Questions",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "SearchQuestionResources", implementation = SearchQuestionResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public PagedModel <EntityModel <SearchQuestionResource>> getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                                          @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                                          @Parameter(hidden = true) @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                          @Parameter(hidden = true) PagedResourcesAssembler <SearchQuestionResource> pagedResourcesAssembler) {
        var page =
                questionnaireResourceMapper.pageQuestionTagsToResources(
                        questionCatalog.getQuestions(tagUuid, questionnaireUuid, pageable, PrincipalId.of(getPrincipal())));

        return pagedResourcesAssembler.toModel(page, this.searchQuestionModelAssembler);
    }

    // %$£! de generics
    // class QuestionWithTagsOnlyPagedModel extends PagedModel<EntityModel<QuestionWithTagsOnlyResource>>{}
    @GetMapping(value = TAGS, produces = {APPLICATION_JSON_VALUE})
    @Timer
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Tags",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "TagResources", implementation = TagResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public PagedModel <EntityModel <TagResource>> getTags(@Parameter(hidden = true) Pageable pageable,
                                                          @Parameter(hidden = true) PagedResourcesAssembler <TagResource> pagedTagResourcesAssembler) {
        var page = questionnaireResourceMapper.pageTagsToResources(questionCatalog.findAllQuestionTagByPage(pageable, PrincipalId.of(getPrincipal())));

        return pagedTagResourcesAssembler.toModel(page, this.tagModelAssembler);
    }

    @GetMapping(value = STATUS, produces = {APPLICATION_JSON_VALUE})
    @Timer
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Tags",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "TagResources", implementation = String.class)))
            )})
    public Iterable <String> getStatus(@Parameter(hidden = true) Pageable pageable) {
        return questionCatalog.findAllStatusByPage(pageable, PrincipalId.of(getPrincipal()));
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTION, key = "#uuid")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public EntityModel <QuestionResource> getQuestionByUuid(@ValidUuid @PathVariable("uuid") String uuid) {
        return of(questionnaireResourceMapper.questionToResources(questionCatalog.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, uuid))));
    }

    @PutMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = QUESTION, condition = "#questionResource != null", key = "#uuid")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public EntityModel <QuestionResource> updateQuestion(@ValidUuid @PathVariable("uuid") String uuid,
                                                         @JsonView(QuestionView.Update.class) @RequestBody @Valid QuestionResource questionResource) {
        var question = questionnaireResourceMapper.questionToModel(uuid, questionResource);
        return of(questionnaireResourceMapper.questionToResources(questionCatalog.updateQuestion(question, PrincipalId.of(getPrincipal()))));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public ResponseEntity <EntityModel <QuestionResource>> createQuestion(@JsonView(QuestionView.Create.class) @RequestBody @Valid QuestionResource questionResource) {
        var question = questionnaireResourceMapper.questionToModel(questionResource);
        var entityModel = of(questionnaireResourceMapper.questionToResources(questionCatalog.saveQuestion(question, PrincipalId.of(getPrincipal()))));

        questionModelAssembler.addLinks(entityModel);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")

    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionCatalog.deleteQuestionById(new QuestionId(uuid));
        return noContent().build();
    }


    @PatchMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object patch", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public EntityModel <QuestionResource> patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionStatus questionStatus) {

        var question = questionCatalog.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, uuid));

        question.setStatus(questionStatus.getStatus());
        return of(questionnaireResourceMapper.questionToResources(questionCatalog.saveQuestion(question, PrincipalId.of(getPrincipal()))));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResource> handleAllException(Exception e) {
        return badRequest().body(new MessageResource(ERROR, e.getMessage()));
    }


}
