package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.controllers.ReportRestController;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + ReportRestController.URL)
@Api(value = "reports-store", tags = "Reports")
@SwaggerDefinition(tags = {
        @Tag(name = "Reports", description = "All operations ")
})
public interface ReportRestApi {
    @ApiOperation(value = "Query bY criteria Criteria", responseContainer = "List", response = QuestionDto.class, nickname = "queryByName", tags = "Reports")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    } )

    @GetMapping(value = "/query", produces = "application/json")
    @ResponseBody
    Iterable<QuestionDto> queryByName(String name);
}
