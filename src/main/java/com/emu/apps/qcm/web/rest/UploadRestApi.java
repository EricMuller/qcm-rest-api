package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/upload")
public interface UploadRestApi {
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/{fileType}", headers = "Content-Type=multipart/form-data", produces = "application/json")
    ResponseEntity <UploadDto> uploadFile( @PathVariable("fileType") String fileType,
                                            @RequestParam("file" ) MultipartFile file,
                                            @RequestParam("async") Boolean async,
                                           Principal principal) throws IOException;

    @GetMapping(produces = "application/json")
    @Timer
    Iterable<UploadDto> getUploads(Pageable pageable, Principal principal)  ;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/{id}/import", produces = "application/json")
    ResponseEntity <MessageDto> importFile( @PathVariable("id") Long uploadId,  Principal principal) throws IOException;

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    ResponseEntity<Upload> deleteUploadById(@PathVariable("id") long id);
}
