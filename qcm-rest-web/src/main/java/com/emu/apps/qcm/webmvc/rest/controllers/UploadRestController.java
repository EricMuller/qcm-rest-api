package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.qcm.business.UploadDelegate;
import com.emu.apps.qcm.webmvc.rest.UploadRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@Profile("webmvc")
public class UploadRestController implements UploadRestApi {

    private UploadDelegate uploadDelegate;

    public UploadRestController(UploadDelegate uploadDelegate) {
        this.uploadDelegate = uploadDelegate;
    }

    @Override
    public UploadDto uploadFile(@PathVariable("fileType") String fileType,
                                @RequestParam("file") MultipartFile multipartFile,
                                @RequestParam(value = "async", required = false) Boolean async,
                                Principal principal) throws IOException {

        return uploadDelegate.uploadFile(fileType, multipartFile, async, principal);
    }

    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {

        return uploadDelegate.getUploads(pageable, principal);
    }

    public UploadDto importFile(@PathVariable("id") Long uploadId, Principal principal) throws IOException {

        return uploadDelegate.importFile(uploadId, principal);

    }

    @Override
    public void deleteUploadById(long id) {
        uploadDelegate.deleteUploadById(id);

    }

    @Override
    public UploadDto getUploadById(long id) {

        return uploadDelegate.getUploadById(id);

    }
}
