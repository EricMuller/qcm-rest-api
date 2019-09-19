package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.CategoryDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/categories")
@Api(value = "categories-store", tags = "Categories")
@SwaggerDefinition(tags = {
        @Tag(name = "Categories", description = "All operations ")
})
public interface CategoryRestApi {
    @ApiOperation(value = "Find a category by ID", response = CategoryDto.class, nickname = "getCategoryById")
    @GetMapping(value = "{id}")
    @ResponseBody
    CategoryDto getCategory(@PathVariable("id") Long id);

    @ApiOperation(value = "Find all categories", responseContainer = "List", response = CategoryDto.class, nickname = "getCategories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping( produces = "application/json")
    @ResponseBody
    Iterable<CategoryDto> getCategories();

    @ApiOperation(value = "save a Category", response = CategoryDto.class, nickname = "saveCategory")
    @PostMapping
    @ResponseBody
    CategoryDto saveCategory(@RequestBody CategoryDto epicDto);
}
