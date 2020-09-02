package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.models.Upload;
import com.emu.apps.qcm.domain.ports.UploadServicePort;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.UPLOADS;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + UPLOADS, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Upload")
public class UploadRestController {

    private final UploadServicePort uploadServicePort;

    public UploadRestController(UploadServicePort uploadServicePort) {
        this.uploadServicePort = uploadServicePort;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    public Upload uploadFileByType(@PathVariable("fileType") String fileType,
                                   @RequestParam("file") MultipartFile multipartFile,
                                   @RequestParam(value = "async", required = false) Boolean async) throws IOException {

        return uploadServicePort.uploadFile(fileType, multipartFile, async, AuthentificationContextHolder.getUser());
    }

    @GetMapping()
    @Timer
    public Iterable <Upload> getUploads(Pageable pageable) {
        return uploadServicePort.getUploads(pageable, AuthentificationContextHolder.getUser());
    }


    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadByUuid(@PathVariable("uuid") String uuid) {
        uploadServicePort.deleteUploadByUuid(uuid);
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public Upload getUploadByUuid(@PathVariable("uuid") String uuid) {
        return uploadServicePort.getUploadByUuid(uuid);
    }


}
