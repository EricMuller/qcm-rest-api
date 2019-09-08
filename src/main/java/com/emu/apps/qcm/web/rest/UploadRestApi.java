package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.dtos.UploadDto;
import com.emu.apps.shared.metrics.Timer;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity <UploadDto> uploadFile( @PathVariable("fileType") String fileType,
                                            @RequestParam("file" ) MultipartFile file,
                                            @RequestParam("async") Boolean async,
                                           Principal principal) throws IOException;

    @ApiOperation(value = "Find all uploads by Page", responseContainer = "List", response = UploadDto.class, nickname = "getUploads")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(produces = "application/json")
    @Timer
    Iterable<UploadDto> getUploads(Pageable pageable, Principal principal)  ;


    @CrossOrigin
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Import File Launched "),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "import a file", responseContainer = "ResponseEntity", response = UploadDto.class, tags = "Upload", nickname = "uploadFile")
    @ResponseBody
    @GetMapping(value = "/{id}/import", produces = "application/json")
    ResponseEntity <MessageDto> importFile( @PathVariable("id") Long uploadId,  Principal principal) throws IOException;

    @ApiOperation(value = "Delete a upload by ID", response = ResponseEntity.class, nickname = "deleteUploadById")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    ResponseEntity<Upload> deleteUploadById(@PathVariable("id") long id);
}
