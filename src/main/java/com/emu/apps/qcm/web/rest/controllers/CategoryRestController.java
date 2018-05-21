package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.epics.Category;
import com.emu.apps.qcm.web.rest.dtos.CategoryDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.mappers.CategoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping("/api/v1/categories")
@Api(value = "categories-store", description = "All operations ", tags = "Categories")
public class CategoryRestController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;


    @ApiOperation(value = "Find a category by ID", response = CategoryDto.class, nickname = "getCategoryById")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryMapper.modelToDto(categoryService.findById(id));
    }

    @ApiOperation(value = "Find all categories", responseContainer = "List", response = CategoryDto.class, nickname = "getCategories")
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
    public Iterable<CategoryDto> getCategories() {
        return categoryMapper.modelsToDtos(categoryService.findAll());

    }

    @ApiOperation(value = "save a Category", response = CategoryDto.class, nickname = "saveCategory")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CategoryDto saveCategory(@RequestBody CategoryDto epicDto) {
        Category epic = categoryMapper.dtoToModel(epicDto);
        return categoryMapper.modelToDto(categoryService.save(epic));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity<?> handleAllException(Exception e) throws IOException {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
