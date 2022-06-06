package com.emu.apps.qcm.rest.controllers.management;

import com.emu.apps.qcm.application.QuestionnaireImport;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.domain.model.upload.UploadRepository;
import com.emu.apps.qcm.rest.controllers.management.hal.UploadActionModelAssembler;
import com.emu.apps.qcm.rest.controllers.management.hal.UploadModelAssembler;
import com.emu.apps.qcm.rest.controllers.management.resources.ActionResource;
import com.emu.apps.qcm.rest.controllers.management.resources.UploadResource;
import com.emu.apps.qcm.rest.mappers.QcmResourceMapper;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.I18nedInternalServerException;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.MANAGEMENT_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.UPLOADS;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.ACTIONS_IMPORT_QUESTIONNAIRE_ERROR;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = MANAGEMENT_API + UPLOADS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Upload")
@Timed("uploads")
@Slf4j
public class UploadRestController {

    private final UploadRepository uploadRepository;

    private final QcmResourceMapper qcmResourceMapper;

    private final UploadModelAssembler uploadModelAssembler;

    private final UploadActionModelAssembler uploadActionModelAssembler;

    private final QuestionnaireImport questionnaireImport;

    public UploadRestController(UploadRepository uploadServicePort, QcmResourceMapper qcmResourceMapper, UploadModelAssembler uploadModelAssembler, UploadActionModelAssembler uploadActionModelAssembler, QuestionnaireImport questionnaireImport) {
        this.uploadRepository = uploadServicePort;
        this.qcmResourceMapper = qcmResourceMapper;
        this.uploadModelAssembler = uploadModelAssembler;
        this.uploadActionModelAssembler = uploadActionModelAssembler;
        this.questionnaireImport = questionnaireImport;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "UploadResource", implementation = UploadResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("uploads.uploadFileByType")
    public UploadResource uploadFileByType(@PathVariable("fileType") String fileType,
                                           @RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam(value = "async", required = false) Boolean async) throws IOException {

        return qcmResourceMapper.uploadToUploadResources(uploadRepository.uploadFile(fileType, multipartFile, async, PrincipalId.of(getPrincipal())));
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List upload",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "UploadResource", implementation = UploadResource.class)))
            )})
    @Timed(value = "uploads.getUploads", longTask = true)
    public PagedModel <EntityModel <UploadResource>> getUploads(@Parameter(hidden = true)
                                                                @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                @Parameter(hidden = true) PagedResourcesAssembler <UploadResource> pagedResourcesAssembler) {

        Link selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());

        var page = qcmResourceMapper.uploadToUploadResources(uploadRepository.getUploads(pageable, PrincipalId.of(getPrincipal())));

        return pagedResourcesAssembler.toModel(page, this.uploadModelAssembler, selfLink);
    }


    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Timed(value = "uploads.deleteUploadByUuid")
    public void deleteUploadByUuid(@PathVariable("uuid") String uuid) {
        uploadRepository.deleteUploadOfId(new UploadId(uuid));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "UploadResource", implementation = UploadResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "uploads.getUploadByUuid")
    public UploadResource getUploadByUuid(@PathVariable("uuid") String uuid) {
        return qcmResourceMapper.uploadToUploadResources(uploadRepository.getUploadOfId(new UploadId(uuid)));
    }


    @ResponseBody
    @GetMapping(value = "/{uuid}/actions/")
    @Timed(value = "uploads.getActions")
    public PagedModel <EntityModel <ActionResource>> getActions(@PathVariable("uuid") String uploadUuid,
                                                                @Parameter(hidden = true)
                                                                @PageableDefault(direction = DESC, sort = {"action"}, size = 100) Pageable pageable,
                                                                @Parameter(hidden = true) PagedResourcesAssembler <ActionResource> pagedResourcesAssembler) {

        var actions = List.of(new ActionResource(uploadUuid, "import", "File Import"));
        var page = new PageAction(actions, pageable, actions.size());
        var selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        return pagedResourcesAssembler.toModel(page, this.uploadActionModelAssembler, selfLink);
    }

    @ResponseBody
    @PutMapping(value = "/{uuid}/actions/import/invoke")
    @Timed(value = "uploads.importFileByUploadUuid", longTask = true)
    public UploadResource importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) {

        LOGGER.warn("CPU Core: {} ", Runtime.getRuntime().availableProcessors());
        LOGGER.warn("CommonPool Parallelism: {}", ForkJoinPool.commonPool().getParallelism());
        LOGGER.warn("CommonPool Common Parallelism: {}", ForkJoinPool.getCommonPoolParallelism());
        try {
            Future <Upload> uploadCompletableFuture = questionnaireImport.importFileAsync(new UploadId(uploadUuid), PrincipalId.of(getPrincipal()));
            var upload = uploadCompletableFuture.get();
            return qcmResourceMapper.uploadToUploadResources(upload);
        } catch (IOException | ExecutionException | InterruptedException e) {
            LOGGER.error("", e);
            throw new I18nedInternalServerException(ACTIONS_IMPORT_QUESTIONNAIRE_ERROR, uploadUuid);
        }
    }

    class PageAction extends PageImpl <ActionResource> {
        public PageAction(List <ActionResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

}
