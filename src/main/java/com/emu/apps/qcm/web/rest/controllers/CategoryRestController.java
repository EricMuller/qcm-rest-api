package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.jpa.entity.category.Category;
import com.emu.apps.qcm.web.rest.CategoryRestApi;
import com.emu.apps.qcm.web.rest.dtos.CategoryDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.mappers.CategoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class CategoryRestController implements CategoryRestApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryMapper.modelToDto(categoryService.findById(id));
    }

    @Override
    public Iterable<CategoryDto> getCategories() {
        return categoryMapper.modelsToDtos(categoryService.findAll());

    }

    @Override
    public CategoryDto saveCategory(@RequestBody CategoryDto epicDto) {
        Category epic = categoryMapper.dtoToModel(epicDto);
        return categoryMapper.modelToDto(categoryService.save(epic));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity<?> handleAllException(Exception e)  {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
