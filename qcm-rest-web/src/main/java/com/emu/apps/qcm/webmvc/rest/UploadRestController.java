package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.dtos.UploadDto;
import com.emu.apps.qcm.domain.ports.ImportServicePort;
import com.emu.apps.qcm.domain.ports.UploadServicePort;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_UPLOADS;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_UPLOADS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadRestController {

    private final UploadServicePort uploadServicePort;

    private final ImportServicePort importServicePort;

    public UploadRestController(UploadServicePort uploadServicePort, ImportServicePort importServicePort) {
        this.uploadServicePort = uploadServicePort;
        this.importServicePort = importServicePort;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    public UploadDto uploadFile(@PathVariable("fileType") String fileType,
                                @RequestParam("file") MultipartFile multipartFile,
                                @RequestParam(value = "async", required = false) Boolean async,
                                Principal principal) throws IOException {

        return uploadServicePort.uploadFile(fileType, multipartFile, async, principal);
    }

    @GetMapping()
    @Timer
    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {
        return uploadServicePort.getUploads(pageable, principal);
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}/import")
    public UploadDto importFile(@PathVariable("uuid") String uploadUuid, Principal principal) throws IOException {
        return importServicePort.importFile(uploadUuid, principal);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadById(@PathVariable("uuid") String uuid) {
        uploadServicePort.deleteUploadByUuid(uuid);
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public UploadDto getUploadById(@PathVariable("uuid") String uuid) {
        return uploadServicePort.getUploadByUuid(uuid);
    }

}