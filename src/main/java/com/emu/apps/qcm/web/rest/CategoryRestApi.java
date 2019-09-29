package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.CategoryDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/categories")
public interface CategoryRestApi {

    @GetMapping(value = "{id}")
    @ResponseBody
    CategoryDto getCategory(@PathVariable("id") Long id);

    @GetMapping( produces = "application/json")
    @ResponseBody
    Iterable<CategoryDto> getCategories();

    @PostMapping
    @ResponseBody
    CategoryDto saveCategory(@RequestBody CategoryDto epicDto);
}
