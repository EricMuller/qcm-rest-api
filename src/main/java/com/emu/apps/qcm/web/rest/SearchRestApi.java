package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.controllers.SearchRestController;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + SearchRestController.URL)
@Api(value = "search-store", tags = "Search")
@SwaggerDefinition(tags = {
        @Tag(name = "Search", description = "All operations ")
})
public interface SearchRestApi {
    @ApiOperation(value = "Search Questions bY criteria Criteria", responseContainer = "List", response = QuestionDto.class, nickname = "searchQuestionsByCriteria", tags = "Search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/questions", produces = "application/json")
    @ResponseBody
    Iterable<QuestionDto> searchQuestionsByCriteria(String libelle);
}
