package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.domain.model.upload.UploadRepository;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.UPLOADS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + UPLOADS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Upload")
public class UploadRestController {

    private final UploadRepository uploadRepository;

    public UploadRestController(UploadRepository uploadServicePort) {
        this.uploadRepository = uploadServicePort;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    public Upload uploadFileByType(@PathVariable("fileType") String fileType,
                                   @RequestParam("file") MultipartFile multipartFile,
                                   @RequestParam(value = "async", required = false) Boolean async) throws IOException {

        return uploadRepository.uploadFile(fileType, multipartFile, async, new PrincipalId(getPrincipal()));
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Iterable <Upload> getUploads(@Parameter(hidden = true)
                                        @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {
        return uploadRepository.getUploads(pageable, new PrincipalId(getPrincipal()));
    }


    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadByUuid(@PathVariable("uuid") String uuid) {
        uploadRepository.deleteUploadByUuid(new UploadId(uuid));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public Upload getUploadByUuid(@PathVariable("uuid") String uuid) {
        return uploadRepository.getUploadByUuid(new UploadId(uuid));
    }


}
