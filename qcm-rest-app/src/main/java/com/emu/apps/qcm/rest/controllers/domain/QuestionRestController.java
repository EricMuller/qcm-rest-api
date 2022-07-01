package com.emu.apps.qcm.rest.controllers.domain;

import com.emu.apps.qcm.application.QuestionService;
import com.emu.apps.qcm.application.ReferentielService;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.rest.config.cache.CacheName;
import com.emu.apps.qcm.rest.controllers.domain.hal.QuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.services.hal.SearchQuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.hal.TagModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView;
import com.emu.apps.qcm.rest.controllers.domain.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.MessageResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.TagResource;
import com.emu.apps.qcm.rest.controllers.domain.mappers.QcmResourceMapper;
import com.emu.apps.qcm.rest.validators.ValidUuid;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTION;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.*;
import static com.emu.apps.qcm.rest.controllers.domain.resources.MessageResource.ERROR;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTION;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static org.springframework.hateoas.EntityModel.of;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping(value = DOMAIN_API + QUESTIONS, produces = {APPLICATION_JSON_VALUE, HAL_JSON_VALUE})
@io.swagger.v3.oas.annotations.tags.Tag(name = "Question")
@Validated
@Timed("questions")
public class QuestionRestController {

    private static final String TAGS_URL = "/tags";

    private final QuestionService questionService;

    private final ReferentielService referentielService;

    private final QcmResourceMapper qcmResourceMapper;

    private final SearchQuestionModelAssembler searchQuestionModelAssembler;

    private final QuestionModelAssembler questionModelAssembler;

    private final TagModelAssembler tagModelAssembler;

    public QuestionRestController(QuestionService questionService, ReferentielService referentielService, QcmResourceMapper qcmResourceMapper,
                                  SearchQuestionModelAssembler searchQuestionModelAssembler,
                                  QuestionModelAssembler questionModelAssembler, TagModelAssembler tagModelAssembler) {
        this.questionService = questionService;
        this.referentielService = referentielService;
        this.qcmResourceMapper = qcmResourceMapper;
        this.searchQuestionModelAssembler = searchQuestionModelAssembler;
        this.questionModelAssembler = questionModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
    }


    @GetMapping(value = "/status", produces = {APPLICATION_JSON_VALUE})
    @Timer
    @PageableAsQueryParam
    @Operation(summary = "Get all status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List status",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "TagResources", implementation = String.class)))
            )})
    @Timed(value = "questions.getStatus", longTask = true)
    public Iterable <String> getStatus(@Parameter(hidden = true) Pageable pageable) {
        return questionService.findAllStatusByPage(pageable, PrincipalId.of(getPrincipal()));
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#uuid")
    @ResponseBody
    @Operation(summary = "Get a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questions.getQuestionByUuid")
    public EntityModel <QuestionResource> getQuestionByUuid(@ValidUuid @PathVariable("uuid") String uuid) {


        var entityModel = of(qcmResourceMapper.questionToQuestionResource(questionService.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, uuid))));

        questionModelAssembler.addLinks(entityModel);

        return entityModel;

    }

    @PutMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionResource != null", key = "#uuid")
    @ResponseBody
    @Operation(summary = "Update a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questions.updateQuestion", longTask = true)
    public EntityModel <QuestionResource> updateQuestion(@ValidUuid @PathVariable("uuid") String uuid,
                                                         @JsonView(QuestionView.UpdateQuestion.class) @RequestBody @Valid QuestionResource questionResource) {
        var question = qcmResourceMapper.questionResourceToModel(uuid, questionResource);

        var entityModel = of(qcmResourceMapper.questionToQuestionResource(questionService.updateQuestion(question, PrincipalId.of(getPrincipal()))));

        questionModelAssembler.addLinks(entityModel);
        return entityModel;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(summary = "Add a new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questions.createQuestion")
    public ResponseEntity <EntityModel <QuestionResource>> createQuestion(@JsonView(QuestionView.CreateQuestion.class) @RequestBody @Valid QuestionResource questionResource) {
        var question = qcmResourceMapper.questionResourceToModel(questionResource);
        var entityModel = of(qcmResourceMapper.questionToQuestionResource(questionService.saveQuestion(question, PrincipalId.of(getPrincipal()))));

        questionModelAssembler.addLinks(entityModel);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @Operation(summary = "Delete a question")
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    @Timed(value = "questions.deleteQuestionByUuid")
    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionService.deleteQuestionById(new QuestionId(uuid));
        return noContent().build();
    }


    @PatchMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object patch", content = @Content(schema = @Schema(name = "QuestionResource", implementation = QuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questions.patchQuestionByUuid")
    public EntityModel <QuestionResource> patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionStatus questionStatus) {

        var question = questionService.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, uuid));

        question.setStatus(questionStatus.getStatus());
        var entityModel = of(qcmResourceMapper.questionToQuestionResource(questionService.saveQuestion(question, PrincipalId.of(getPrincipal()))));

        questionModelAssembler.addLinks(entityModel);

        return entityModel;
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResource> handleAllException(Exception e) {
        return badRequest().body(new MessageResource(ERROR, e.getMessage()));
    }

    //     %$£! de generics
//     class QuestionWithTagsOnlyPagedModel extends PagedModel<EntityModel<QuestionWithTagsOnlyResource>>{}
    @GetMapping(value = TAGS_URL, produces = {APPLICATION_JSON_VALUE})
    @Timer
    @PageableAsQueryParam
    @Operation(summary = "Get all tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Tags",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "TagResources", implementation = TagResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questions.getTags", longTask = true)
    public PagedModel <EntityModel <TagResource>> getTags(@RequestParam(value = "search", required = false) String search,
                                                          @Parameter(hidden = true) Pageable pageable,
                                                          @Parameter(hidden = true) PagedResourcesAssembler <TagResource> pagedTagResourcesAssembler) throws IOException {
        var page = qcmResourceMapper.pageTagsToResources(questionService.getTags(search, pageable, PrincipalId.of(getPrincipal())));

        return pagedTagResourcesAssembler.toModel(page, this.tagModelAssembler);
    }

    @GetMapping(value = TAGS_URL + "/{uuid}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "TagResource", implementation = TagResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "tags.getTagByUuid")
    public EntityModel <TagResource> getTagByUuid(@PathVariable("uuid") String uuid) {
        return EntityModel.of(qcmResourceMapper.tagToTagResources(referentielService.getTagQuestionOfId(new TagId(uuid))));
    }


    @GetMapping(CATEGORIES)
    @ResponseBody
    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    @Timed("questions.getCategories")
    public Iterable <CategoryResource> getQuestionCategories() {

        return qcmResourceMapper.categoriesToCategoryResources(
                referentielService.getCategories(PrincipalId.of(getPrincipal()), QUESTION.name()));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonRootName(value = "Question_status")
    public class QuestionStatus {

        @JsonProperty("status")
        private String status;

    }

}
