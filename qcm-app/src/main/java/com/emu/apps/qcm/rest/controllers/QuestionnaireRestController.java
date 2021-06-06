package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.application.QuestionnaireCatalog;
import com.emu.apps.qcm.application.reporting.ExportService;
import com.emu.apps.qcm.application.reporting.dtos.Export;
import com.emu.apps.qcm.application.reporting.template.FileFormat;
import com.emu.apps.qcm.application.reporting.template.TemplateReportServices;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.resources.QuestionnaireQuestionResource;
import com.emu.apps.qcm.rest.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.resources.command.QuestionnaireQuestionUpdate;
import com.emu.apps.qcm.rest.resources.openui.QuestionnaireView;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emu.apps.qcm.application.reporting.template.FileFormat.getFileFormat;
import static com.emu.apps.qcm.application.reporting.template.ReportTemplate.TEMPLATE_QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.config.cache.CacheName.Names.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
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
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
public class QuestionnaireRestController {

    private final QuestionnaireCatalog questionnaireCatalog;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    private final ExportService exportService;

    private final TemplateReportServices templateReportServices;

    public QuestionnaireRestController(QuestionnaireCatalog questionnaireCatalog, QuestionnaireResourceMapper questionnaireResourceMapper, ExportService exportService, TemplateReportServices templateReportServices) {
        this.questionnaireCatalog = questionnaireCatalog;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
        this.exportService = exportService;
        this.templateReportServices = templateReportServices;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public QuestionnaireResource getQuestionnaireById(@PathVariable("uuid") String uuid) {

        return questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.getQuestionnaireById(new QuestionnaireId(uuid))
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTIONNAIRE)))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel())
                .add(linkTo(methodOn(QuestionnaireRestController.class).getExportByQuestionnaireUuid(uuid)).withRel("export"));
    }

    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <QuestionnaireResource> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireCatalog.deleteQuestionnaireById(new QuestionnaireId(uuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    @PutMapping(value = "/{uuid}")
    @CachePut(cacheNames = QUESTIONNAIRE, condition = "#questionnaireResource != null", key = "#uuid")
    @Timer
    @ResponseBody
    public QuestionnaireResource updateQuestionnaire(@PathVariable("uuid") String uuid,
                                                     @JsonView(QuestionnaireView.Update.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = questionnaireResourceMapper.questionnaireToModel(uuid, questionnaireResource);
        return questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.updateQuestionnaire(questionnaire, new PrincipalId(getPrincipal())))
                .add(linkTo(QuestionnaireRestController.class).slash(uuid).withSelfRel());
    }

    @PostMapping
    @ResponseBody
    public QuestionnaireResource createQuestionnaire(@JsonView(QuestionnaireView.Create.class) @RequestBody QuestionnaireResource questionnaireResource) {
        var questionnaire = questionnaireResourceMapper.questionnaireToModel(questionnaireResource);

        var createdQuestionnaire = questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.saveQuestionnaire(questionnaire, new PrincipalId(getPrincipal())));

        return createdQuestionnaire.add(linkTo(QuestionnaireRestController.class).slash(createdQuestionnaire.getUuid()).withSelfRel());
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam

    public PageQuestionnaireQuestion getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid,
                                                                   @Parameter(hidden = true)
                                                                   @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

              var questionnaireQuestionResources =
                questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireCatalog.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable));

        return new PageQuestionnaireQuestion(questionnaireQuestionResources.getContent(), pageable, questionnaireQuestionResources.getContent().size());
    }


    @GetMapping(value = "/{uuid}" + QUESTIONS + "/{q_uuid}")
    @Timer
    @ResponseBody
    public QuestionnaireQuestionResource getQuestionnaireQuestionById(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {

        return questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireCatalog.getQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public PageQuestionnaire getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                               @Parameter(hidden = true)
                                               @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        var questionnaireResources = questionnaireResourceMapper.questionnaireToResources(questionnaireCatalog.getQuestionnaires(tagUuid, pageable, new PrincipalId(getPrincipal())));

        return new PageQuestionnaire(questionnaireResources.getContent(), pageable, questionnaireResources.getContent().size());

    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public QuestionnaireQuestionResource addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody QuestionnaireQuestionUpdate questionnaireQuestionUpdate) {


        QuestionnaireQuestion questionnaireQuestion = questionnaireCatalog.addQuestion(new QuestionnaireId(uuid)
                , new QuestionId(questionnaireQuestionUpdate.getUuid()), of(questionnaireQuestionUpdate.getPosition())
                , new PrincipalId(getPrincipal()));

        return questionnaireResourceMapper.questionnaireQuestionToResources(questionnaireQuestion);
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireCatalog.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    final class PageQuestionnaireQuestion extends PageImpl <QuestionnaireQuestionResource> {
        public PageQuestionnaireQuestion(List <QuestionnaireQuestionResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

    final class PageQuestionnaire extends PageImpl <QuestionnaireResource> {
        public PageQuestionnaire(List <QuestionnaireResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

    @Timer
    @GetMapping(value = "/{uuid}" + EXPORTS, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Export getExportByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return exportService.getbyQuestionnaireUuid(uuid);
    }


    @Timer
    @GetMapping(value = "/{uuid}" + EXPORTS + "/{type-report}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    public ResponseEntity <Resource> getReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        if (isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        FileFormat reportFormat = getFileFormat(type.toUpperCase(getDefault()));

        final Export export = exportService.getbyQuestionnaireUuid(uuid);

        ByteArrayResource resource = new ByteArrayResource(templateReportServices
                .convertAsStream(export, TEMPLATE_QUESTIONNAIRE, reportFormat));

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + export.getName() + "." + reportFormat.getExtention() + "\"")
                .body(resource);
    }

}
