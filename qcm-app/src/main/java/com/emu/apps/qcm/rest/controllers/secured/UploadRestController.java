package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.domain.model.upload.UploadRepository;
import com.emu.apps.qcm.rest.controllers.secured.hal.UploadModelAssembler;
import com.emu.apps.qcm.rest.controllers.secured.resources.TagResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.UploadResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PROTECTED_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.UPLOADS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_API + UPLOADS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Upload")
public class UploadRestController {

    private final UploadRepository uploadRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    private final UploadModelAssembler uploadModelAssembler;

    public UploadRestController(UploadRepository uploadServicePort, QuestionnaireResourceMapper questionnaireResourceMapper, UploadModelAssembler uploadModelAssembler) {
        this.uploadRepository = uploadServicePort;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
        this.uploadModelAssembler = uploadModelAssembler;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "UploadResource", implementation = UploadResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public UploadResource uploadFileByType(@PathVariable("fileType") String fileType,
                                           @RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam(value = "async", required = false) Boolean async) throws IOException {

        return questionnaireResourceMapper.uploadToResources(uploadRepository.uploadFile(fileType, multipartFile, async, PrincipalId.of(getPrincipal())));
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List upload",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "UploadResource", implementation = UploadResource.class)))
            )})
    public PagedModel <EntityModel <UploadResource>> getUploads(@Parameter(hidden = true)
                                                                @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                @Parameter(hidden = true) PagedResourcesAssembler <UploadResource> pagedResourcesAssembler) {

        Link selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());

        var page = questionnaireResourceMapper.uploadToResources(uploadRepository.getUploads(pageable, PrincipalId.of(getPrincipal())));

        return pagedResourcesAssembler.toModel(page, this.uploadModelAssembler, selfLink);
    }


    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadByUuid(@PathVariable("uuid") String uuid) {
        uploadRepository.deleteUploadByUuid(new UploadId(uuid));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "UploadResource", implementation = UploadResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public UploadResource getUploadByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourceMapper.uploadToResources(uploadRepository.getUploadByUuid(new UploadId(uuid)));
    }

}
