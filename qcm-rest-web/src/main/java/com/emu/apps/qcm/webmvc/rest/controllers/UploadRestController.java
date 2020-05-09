package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.business.UploadDelegate;
import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.UPLOADS;

@RestController
@Profile("webmvc")
@RequestMapping(value = UPLOADS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadRestController {

    private UploadDelegate uploadDelegate;

    public UploadRestController(UploadDelegate uploadDelegate) {
        this.uploadDelegate = uploadDelegate;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    public UploadDto uploadFile(@PathVariable("fileType") String fileType,
                                @RequestParam("file") MultipartFile multipartFile,
                                @RequestParam(value = "async", required = false) Boolean async,
                                Principal principal) throws IOException {

        return uploadDelegate.uploadFile(fileType, multipartFile, async, principal);
    }

    @GetMapping()
    @Timer
    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {
        return uploadDelegate.getUploads(pageable, principal);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/import")
    public UploadDto importFile(@PathVariable("id") Long uploadId, Principal principal) throws IOException {
        return uploadDelegate.importFile(uploadId, principal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadById(@PathVariable("id") long id) {
        uploadDelegate.deleteUploadById(id);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UploadDto getUploadById(@PathVariable("id") long id) {
        return uploadDelegate.getUploadById(id);
    }
}
