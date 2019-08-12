package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + "/suggest")
@Api(value = "suggest-store", tags = "suggest")
@SwaggerDefinition(tags = {
        @Tag(name = "Suggest", description = "All operations ")
})
public interface SuggestRestApi {

    @ApiOperation(value = "Find suggestions by title", responseContainer = "List", response = SuggestDto.class, nickname = "getSuggestions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/title", produces = "application/json")
    @ResponseBody
    Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText);
}
