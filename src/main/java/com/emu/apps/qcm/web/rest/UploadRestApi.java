package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import io.swagger.annotations.*;
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
    @CrossOrigin
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Upload File Created "),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "upload a file", responseContainer = "ResponseEntity", response = MessageDto.class, tags = "Upload", nickname = "uploadFile")
    @ResponseBody
    @PostMapping(value = "/{fileType}", headers = "Content-Type=multipart/form-data", produces = "application/json")
    ResponseEntity <MessageDto> uploadFile( @PathVariable("fileType") String fileType,
                                            @RequestParam("file" ) MultipartFile file,
                                            @RequestParam("async") Boolean async,
                                           Principal principal) throws IOException;
}
