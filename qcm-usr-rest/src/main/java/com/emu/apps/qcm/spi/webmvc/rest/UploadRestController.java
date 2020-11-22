package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.aggregates.Upload;
import com.emu.apps.qcm.repositories.UploadRepository;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.UPLOADS;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + UPLOADS, produces = MediaType.APPLICATION_JSON_VALUE)
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

        return uploadRepository.uploadFile(fileType, multipartFile, async, AuthentificationContextHolder.getUser());
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Iterable <Upload> getUploads(@Parameter(hidden = true)
                                        @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {
        return uploadRepository.getUploads(pageable, AuthentificationContextHolder.getUser());
    }


    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadByUuid(@PathVariable("uuid") String uuid) {
        uploadRepository.deleteUploadByUuid(uuid);
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public Upload getUploadByUuid(@PathVariable("uuid") String uuid) {
        return uploadRepository.getUploadByUuid(uuid);
    }


}
