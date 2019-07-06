package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/upload")
@Api(value = "upload-store",tags = {"Upload"})
@SwaggerDefinition(tags = {
        @Tag(name = "Upload", description = "All operations ")
})
public interface UploadRestApi {
    @ApiOperation(value = "upload a file", responseContainer = "ResponseEntity", response = MessageDto.class, tags = "Upload", nickname = "uploadFile")
    @ResponseBody
    @PostMapping(value = "/{fileType}", headers = "Content-Type=multipart/form-data", produces = "application/json")
    ResponseEntity <MessageDto> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileType") String fileType, Principal principal) throws IOException;
}
