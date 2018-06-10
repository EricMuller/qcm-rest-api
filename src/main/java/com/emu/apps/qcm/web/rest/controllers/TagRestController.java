package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.repositories.specifications.tags.TagSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import com.emu.apps.qcm.web.rest.mappers.TagMapper;
import com.emu.apps.qcm.web.rest.utils.StringToFilter;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping("/api/v1/tags")
@Api(value = "tag-store", description = "All operations ", tags = "Tag")
public class TagRestController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private StringToFilter stringToFilter;

    @Autowired
    private TagSpecification tagSpecification;

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
    @PreAuthorize("true")
    public Page<TagDto> getTagsByPAge(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException {

        FilterDto[] filterDtos = stringToFilter.getFilterDtos(filterString);
        Specification<Tag> specifications = tagSpecification.getSpecifications(filterDtos, principal);

        return tagMapper.pageToDto(tagService.findAllByPage(specifications, pageable));
    }

    @ApiOperation(value = "Find a tag by ID", response = TagDto.class, nickname = "getTagById")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagMapper.modelToDto(tagService.findById(id));
    }

    @ApiOperation(value = "Save a currentQuestionnaire", response = TagDto.class, nickname = "saveTag")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        Tag tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagService.save(tag));
    }


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
    @PreAuthorize("true")
    public Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {
        final List<SuggestDto> suggestions = Lists.newArrayList();
        if (StringUtils.isNoneEmpty(queryText)) {
            tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }


}
