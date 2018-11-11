package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RequestMapping(QcmApi.API_V1 +"/tags")
@Api(value = "tag-store", description = "All operations ", tags = "Tags")
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
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    Page<TagDto> getTagsByPAge(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException;

    @ApiOperation(value = "Find a tag by ID", response = TagDto.class, nickname = "getTagById")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    TagDto getTagById(@PathVariable("id") Long id);

    @ApiOperation(value = "Save a currentQuestionnaire", response = TagDto.class, nickname = "saveTag")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    TagDto saveTag(@RequestBody TagDto tagDto);

    @ApiOperation(value = "Find suggestions", responseContainer = "List", response = SuggestDto.class, nickname = "getSuggestions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/suggest", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText);
}
