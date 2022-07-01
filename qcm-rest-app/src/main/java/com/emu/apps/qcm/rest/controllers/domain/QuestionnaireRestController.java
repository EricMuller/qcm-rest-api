package com.emu.apps.qcm.rest.controllers.domain;


import com.emu.apps.qcm.application.QuestionnaireService;
import com.emu.apps.qcm.application.ReferentielService;
import com.emu.apps.qcm.application.export.ExportService;
import com.emu.apps.qcm.application.export.dto.Export;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireTagRepository;
import com.emu.apps.qcm.rest.config.cache.CacheName;
import com.emu.apps.qcm.rest.controllers.domain.hal.QuestionnaireActionModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.hal.QuestionnaireModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.hal.QuestionnaireQuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.domain.resources.ActionResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireQuestionResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.TagResource;
import com.emu.apps.qcm.rest.controllers.domain.mappers.QcmResourceMapper;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static com.emu.apps.qcm.application.export.ExportFormat.getFileFormat;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.*;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static java.util.Locale.getDefault;
import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping(value = DOMAIN_API + QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
@Timed("questionnaires")
public class QuestionnaireRestController {

    private final String TAGS_URL = "/tags";

    static class Actions {
        static final String EXPORT = "export";

        static final String REPORT = "report";

    }


    private final QuestionnaireService questionnaireService;

    private final ReferentielService referentielService;

    private final QcmResourceMapper qcmResourceMapper;

    private final ExportService exportService;

    private final QuestionnaireModelAssembler questionnaireModelAssembler;

    private final QuestionnaireQuestionModelAssembler questionnaireQuestionModelAssembler;

    private final QuestionnaireActionModelAssembler questionnaireActionModelAssembler;

    public QuestionnaireRestController(QuestionnaireService questionnaireService, QcmResourceMapper qcmResourceMapper, ExportService exportService, QuestionnaireModelAssembler questionnaireModelAssembler, QuestionnaireQuestionModelAssembler questionnaireQuestionModelAssembler, QuestionnaireActionModelAssembler questionnaireActionModelAssembler, QuestionnaireTagRepository questionnaireTagRepository, ReferentielService referentielService) {
        this.questionnaireService = questionnaireService;
        this.qcmResourceMapper = qcmResourceMapper;
        this.exportService = exportService;
        this.questionnaireModelAssembler = questionnaireModelAssembler;
        this.questionnaireQuestionModelAssembler = questionnaireQuestionModelAssembler;
        this.questionnaireActionModelAssembler = questionnaireActionModelAssembler;
        this.referentielService = referentielService;
    }

    @GetMapping(value = "/{uuid}")
    @Operation(summary = "Get a questionnaire")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaireById")
    public EntityModel <QuestionnaireResource> getQuestionnaireById(@PathVariable("uuid") String uuid) {

        return EntityModel.of(qcmResourceMapper.questionnaireToQuestionnaireResources(questionnaireService.getQuestionnaireById(new QuestionnaireId(uuid))
                        .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTIONNAIRE, uuid))))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel())
                .add(linkTo(methodOn(QuestionnaireRestController.class).createJsonExportByQuestionnaireUuid(uuid)).withRel("export"));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Delete a questionnaire")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    @Timed(value = "questionnaires.deleteQuestionnaireById")
    public ResponseEntity <QuestionnaireResource> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireService.deleteQuestionnaireById(new QuestionnaireId(uuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    @PutMapping(value = "/{uuid}")
    @Operation(summary = "Update a questionnaire")
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireResource != null", key = "#uuid")
    @Timer
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.updateQuestionnaire")
    public EntityModel <QuestionnaireResource> updateQuestionnaire(@PathVariable("uuid") String uuid,
                                                                   @JsonView(QuestionnaireView.UpdateQuestionnaire.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = qcmResourceMapper.questionnaireResourceToModel(uuid, questionnaireResource);
        return EntityModel.of(qcmResourceMapper.questionnaireToQuestionnaireResources(questionnaireService.updateQuestionnaire(questionnaire, PrincipalId.of(getPrincipal()))))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel());
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Add a new questionnaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.createQuestionnaire")
    public ResponseEntity <EntityModel <QuestionnaireResource>> createQuestionnaire(@JsonView(QuestionnaireView.CreateQuestionnaire.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = qcmResourceMapper.questionnaireResourceToModel(questionnaireResource);

        var entityModel = EntityModel.of(qcmResourceMapper.questionnaireToQuestionnaireResources(questionnaireService.saveQuestionnaire(questionnaire, PrincipalId.of(getPrincipal()))));

        questionnaireModelAssembler.addLinks(entityModel);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
    @Operation(summary = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Questions",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "QuestionnaireQuestionResource", implementation = QuestionnaireQuestionResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionsByQuestionnaireId", longTask = true)
    public PagedModel <EntityModel <QuestionnaireQuestionResource>> getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid,
                                                                                                  @Parameter(hidden = true) @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable,
                                                                                                  @Parameter(hidden = true) PagedResourcesAssembler <QuestionnaireQuestionResource> pagedResourcesAssembler) {
        var page =
                qcmResourceMapper.questionnaireQuestionToResources(questionnaireService.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable), uuid);

        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());

        return pagedResourcesAssembler.toModel(page, this.questionnaireQuestionModelAssembler, selfLink);
    }

    @GetMapping(value = "/{uuid}" + QUESTIONS + "/{q_uuid}")
    @Timer
    @ResponseBody
    @Operation(summary = "Get a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "QuestionnaireQuestionResource", implementation = QuestionnaireQuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaireQuestionById")
    public QuestionnaireQuestionResource getQuestionnaireQuestionById(@PathVariable("uuid") String uuid, @PathVariable("q_uuid") String questionUuid) {
        return qcmResourceMapper.questionnaireQuestionToResources(questionnaireService.getQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid)), uuid);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    @Operation(summary = "Get all questionnaires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Questionnaires",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaires", longTask = true)
    public PagedModel <EntityModel <QuestionnaireResource>> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                                              @Parameter(hidden = true) @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                              @Parameter(hidden = true) PagedResourcesAssembler <QuestionnaireResource> pagedResourcesAssembler) {
        var page = qcmResourceMapper.questionnaireToQuestionnaireResources(questionnaireService.getQuestionnaires(tagUuid, pageable, PrincipalId.of(getPrincipal())));
        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        return pagedResourcesAssembler.toModel(page, this.questionnaireModelAssembler, selfLink);
    }

    @PostMapping(value = "/{uuid}/questions")
    @Operation(summary = "Add a question")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionnaireQuestionResource", implementation = QuestionnaireQuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.addQuestionByQuestionnaireId")
    public EntityModel <QuestionnaireQuestionResource> addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody QuestionnaireQuestionUpdate questionnaireQuestionUpdate) {

        var questionnaireQuestion = questionnaireService.addQuestion(new QuestionnaireId(uuid)
                , new QuestionId(questionnaireQuestionUpdate.getUuid()), of(questionnaireQuestionUpdate.getPosition())
                , PrincipalId.of(getPrincipal()));

        var model = EntityModel.of(qcmResourceMapper.questionnaireQuestionToResources(questionnaireQuestion, uuid));
        questionnaireQuestionModelAssembler.addLinks(model);
        return model;
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @Operation(summary = "Delete a question")
    @ResponseBody
    @Timed(value = "questionnaires.deleteQuestionByQuestionnaireId")
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireService.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }


    @Timer
    @PutMapping(value = "/{uuid}/actions/" + Actions.EXPORT + "/invoke", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Invoke export action")
    //@GetMapping(value = "/{uuid}" + EXPORTS, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed(value = "questionnaires.actions.getExportByQuestionnaireUuid", longTask = true)
    public Export createJsonExportByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return exportService.getExportByQuestionnaireUuid(new QuestionnaireId(uuid));
    }

    @Timer
    @PutMapping(value = "/{uuid}/actions/" + Actions.REPORT + "/invoke", produces = APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Invoke report action")
    //@GetMapping(value = "/{uuid}" + EXPORTS + "/{type-report}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    @Timed(value = "questionnaires.actions.getReportByQuestionnaireUuid", longTask = true)
    public ResponseEntity <Resource> createByteArrayReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @RequestParam("type-report") String type) {

        if (isNull(type)) {
            throw new IllegalArgumentException("Invalid type-report argument");
        }

        var reportFormat = getFileFormat(type.toUpperCase(getDefault()));

        ByteArrayResource resource = exportService.exportAsByteArray(new QuestionnaireId(uuid), reportFormat);

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + uuid + "." + reportFormat.getExtension() + "\"")
                .body(resource);
    }


    @ResponseBody
    @Operation(summary = "Get all actions")
    @GetMapping(value = "/{uuid}/actions/")
    @Timed(value = "questionnaires.getActions")
    public PagedModel <EntityModel <ActionResource>> getActions(@PathVariable("uuid") String uploadUuid,
                                                                @Parameter(hidden = true)
                                                                @PageableDefault(direction = DESC, sort = {"action"}, size = 100) Pageable pageable,
                                                                @Parameter(hidden = true) PagedResourcesAssembler <ActionResource> pagedResourcesAssembler) {

        var actions = List.of(new ActionResource(uploadUuid, Actions.EXPORT, "Export questionnaire as Json"),
                new ActionResource(uploadUuid,Actions.REPORT, "Report questionnaire as pdf or dcx"));
        var page = new QuestionnaireRestController.PageAction(actions, pageable, actions.size());
        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        return pagedResourcesAssembler.toModel(page, this.questionnaireActionModelAssembler, selfLink);
    }


    @GetMapping(TAGS_URL)
    @ResponseBody
    @Operation(summary = "Get all tags")
    @PageableAsQueryParam
    @Timed(value = "questionnaire.tags.getTags", longTask = true)
    public QuestionnaireRestController.PageTag getTags(@RequestParam(value = "search", required = false) String search,
                                                       @Parameter(hidden = true)
                                                       @PageableDefault(direction = DESC, sort = {"dateModification"}, size = 100) Pageable pageable) throws IOException {

        Page <TagResource> tagResourcesPage =
                qcmResourceMapper.tagToTagResources(questionnaireService.getTags(search, pageable, PrincipalId.of(getPrincipal())));

        return new QuestionnaireRestController.PageTag(tagResourcesPage.getContent(), pageable, tagResourcesPage.getContent().size());
    }


    @GetMapping(CATEGORIES)
    @ResponseBody
    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    @Timed("questionnaire.category.getCategories")
    public Iterable <CategoryResource> getQuestionCategories() {

        return qcmResourceMapper.categoriesToCategoryResources(
                referentielService.getCategories(PrincipalId.of(getPrincipal()), QUESTIONNAIRE.name()));
    }

    class PageAction extends PageImpl <ActionResource> {
        public PageAction(List <ActionResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

    class PageTag extends PageImpl <TagResource> {
        public PageTag(List <TagResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @NoArgsConstructor
    public class QuestionnaireQuestionUpdate {

        @JsonProperty("uuid")
        private String uuid;

        @JsonProperty(value = "position")
        private Integer position;

        @JsonProperty(value = "points")
        private Integer points;


    }

}

