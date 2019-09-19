package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.TagDto;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/tags")
@Api(value = "tag-store", tags = "Tags")
@SwaggerDefinition(tags = {
        @Tag(name = "Tags", description = "All operations ")
})
public interface TagRestApi {
    @ApiOperation(value = "Find all Tags By Page", responseContainer = "List", response = TagDto.class, nickname = "getTags")
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
    @ResponseBody
    Page<TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal ) throws IOException;

    @ApiOperation(value = "Find a tag by ID", response = TagDto.class, nickname = "getTagById")
    @GetMapping(value = "{id}")
    @ResponseBody
    TagDto getTagById(@PathVariable("id") Long id);

    @ApiOperation(value = "Save a current Tag", response = TagDto.class, nickname = "saveTag")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    TagDto saveTag(@RequestBody TagDto tagDto);

}
