package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.adapters.ImportServiceAdapter;
import com.emu.apps.qcm.domain.adapters.UploadServiceAdapter;
import com.emu.apps.qcm.domain.ports.ImportService;
import com.emu.apps.qcm.domain.ports.UploadService;
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

import static com.emu.apps.qcm.webmvc.rest.RestMappings.UPLOADS;

@RestController
@Profile("webmvc")
@RequestMapping(value = UPLOADS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadRestController {

    private final UploadService uploadService;

    private final ImportService importService;

    public UploadRestController(UploadServiceAdapter uploadService, ImportService importService) {
        this.uploadService = uploadService;
        this.importService = importService;
    }

    @ResponseBody
    @PostMapping(value = "/{fileType}")
    public UploadDto uploadFile(@PathVariable("fileType") String fileType,
                                @RequestParam("file") MultipartFile multipartFile,
                                @RequestParam(value = "async", required = false) Boolean async,
                                Principal principal) throws IOException {

        return uploadService.uploadFile(fileType, multipartFile, async, principal);
    }

    @GetMapping()
    @Timer
    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {
        return uploadService.getUploads(pageable, principal);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/import")
    public UploadDto importFile(@PathVariable("id") Long uploadId, Principal principal) throws IOException {
        return importService.importFile(uploadId, principal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadById(@PathVariable("id") long id) {
        uploadService.deleteUploadById(id);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UploadDto getUploadById(@PathVariable("id") long id) {
        return uploadService.getUploadById(id);
    }

}
