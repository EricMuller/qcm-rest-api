package com.emu.apps.qcm.rest.controllers.management;


import com.emu.apps.qcm.application.QuestionnaireCatalog;
import com.emu.apps.qcm.application.reporting.ExportService;
import com.emu.apps.qcm.application.reporting.dtos.Export;
import com.emu.apps.qcm.application.reporting.template.TemplateReportServices;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.MpttCategoryRepository;
import com.emu.apps.qcm.domain.model.category.TypeCategory;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.rest.config.cache.CacheName;
import com.emu.apps.qcm.rest.controllers.management.command.QuestionnaireQuestionUpdate;
import com.emu.apps.qcm.rest.controllers.management.hal.QuestionnaireModelAssembler;
import com.emu.apps.qcm.rest.controllers.management.hal.QuestionnaireQuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.management.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionnaireQuestionResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.FunctionnalException;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
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
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

import static com.emu.apps.qcm.application.reporting.template.FileFormat.getFileFormat;
import static com.emu.apps.qcm.application.reporting.template.ReportTemplate.TEMPLATE_QUESTIONNAIRE;
import static com.emu.apps.qcm.domain.model.category.TypeCategory.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
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
@RequestMapping(value = MANAGEMENT_API + QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
@Timed("questionnaires")
public class QuestionnaireRestController {

    private final QuestionnaireCatalog questionnaireCatalog;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    private final ExportService exportService;

    private final TemplateReportServices templateReportServices;

    private final QuestionnaireModelAssembler questionnaireModelAssembler;

    private final QuestionnaireQuestionModelAssembler questionnaireQuestionModelAssembler;

    private final MpttCategoryRepository mpttCategoryRepository;

    public QuestionnaireRestController(QuestionnaireCatalog questionnaireCatalog, QuestionnaireResourceMapper questionnaireResourceMapper, ExportService exportService, TemplateReportServices templateReportServices, QuestionnaireModelAssembler questionnaireModelAssembler, QuestionnaireQuestionModelAssembler questionnaireQuestionModelAssembler, MpttCategoryRepository mpttCategoryRepository) {
        this.questionnaireCatalog = questionnaireCatalog;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
        this.exportService = exportService;
        this.templateReportServices = templateReportServices;
        this.questionnaireModelAssembler = questionnaireModelAssembler;
        this.questionnaireQuestionModelAssembler = questionnaireQuestionModelAssembler;
        this.mpttCategoryRepository = mpttCategoryRepository;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaireById")
    public EntityModel <QuestionnaireResource> getQuestionnaireById(@PathVariable("uuid") String uuid) {

        return EntityModel.of(questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.getQuestionnaireById(new QuestionnaireId(uuid))
                        .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTIONNAIRE, uuid))))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel())
                .add(linkTo(methodOn(QuestionnaireRestController.class).getExportByQuestionnaireUuid(uuid)).withRel("export"));
    }

    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    @Timed(value = "questionnaires.deleteQuestionnaireById")
    public ResponseEntity <QuestionnaireResource> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireCatalog.deleteQuestionnaireById(new QuestionnaireId(uuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    @PutMapping(value = "/{uuid}")
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireResource != null", key = "#uuid")
    @Timer
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.updateQuestionnaire")
    public EntityModel <QuestionnaireResource> updateQuestionnaire(@PathVariable("uuid") String uuid,
                                                                   @JsonView(QuestionnaireView.Update.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = questionnaireResourceMapper.questionnaireToModel(uuid, questionnaireResource);
        return EntityModel.of(questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.updateQuestionnaire(questionnaire, PrincipalId.of(getPrincipal()))))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel());
    }

    @PostMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.createQuestionnaire")
    public ResponseEntity <EntityModel <QuestionnaireResource>> createQuestionnaire(@JsonView(QuestionnaireView.Create.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = questionnaireResourceMapper.questionnaireToModel(questionnaireResource);

        var entityModel = EntityModel.of(questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.saveQuestionnaire(questionnaire, PrincipalId.of(getPrincipal()))));

        questionnaireModelAssembler.addLinks(entityModel);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
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
                questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireCatalog.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable), uuid);

        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());

        return pagedResourcesAssembler.toModel(page, this.questionnaireQuestionModelAssembler, selfLink);
    }

    @GetMapping(value = "/{uuid}" + QUESTIONS + "/{q_uuid}")
    @Timer
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "QuestionnaireQuestionResource", implementation = QuestionnaireQuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaireQuestionById")
    public QuestionnaireQuestionResource getQuestionnaireQuestionById(@PathVariable("uuid") String uuid, @PathVariable("q_uuid") String questionUuid) {
        return questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireCatalog.getQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid)), uuid);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Questionnaires",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "QuestionnaireResource", implementation = QuestionnaireResource.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.getQuestionnaires", longTask = true)
    public PagedModel <EntityModel <QuestionnaireResource>> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                                              @Parameter(hidden = true) @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                              @Parameter(hidden = true) PagedResourcesAssembler <QuestionnaireResource> pagedResourcesAssembler) {
        var page = questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.getQuestionnaires(tagUuid, pageable, PrincipalId.of(getPrincipal())));
        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        return pagedResourcesAssembler.toModel(page, this.questionnaireModelAssembler, selfLink);
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "QuestionnaireQuestionResource", implementation = QuestionnaireQuestionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "questionnaires.addQuestionByQuestionnaireId")
    public EntityModel <QuestionnaireQuestionResource> addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody QuestionnaireQuestionUpdate questionnaireQuestionUpdate) {

        var questionnaireQuestion = questionnaireCatalog.addQuestion(new QuestionnaireId(uuid)
                , new QuestionId(questionnaireQuestionUpdate.getUuid()), of(questionnaireQuestionUpdate.getPosition())
                , PrincipalId.of(getPrincipal()));

        var model = EntityModel.of(questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireQuestion, uuid));
        questionnaireQuestionModelAssembler.addLinks(model);
        return model;
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    @Timed(value = "questionnaires.deleteQuestionByQuestionnaireId")
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireCatalog.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }



    @Timer
    @GetMapping(value = "/{uuid}" + EXPORTS, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed(value = "questionnaires.getExportByQuestionnaireUuid", longTask = true)
    public Export getExportByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return exportService.getbyQuestionnaireUuid(uuid);
    }

    @Timer
    @GetMapping(value = "/{uuid}" + EXPORTS + "/{type-report}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    @Timed(value = "questionnaires.getReportByQuestionnaireUuid", longTask = true)
    public ResponseEntity <Resource> getReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        if (isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        var reportFormat = getFileFormat(type.toUpperCase(getDefault()));

        final var export = exportService.getbyQuestionnaireUuid(uuid);

        ByteArrayResource resource = new ByteArrayResource(templateReportServices
                .convertAsStream(export, TEMPLATE_QUESTIONNAIRE, reportFormat));

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + export.getName() + "." + reportFormat.getExtention() + "\"")
                .body(resource);
    }

}
