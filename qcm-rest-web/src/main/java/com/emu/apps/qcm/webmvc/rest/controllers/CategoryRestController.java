package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.jpa.specifications.PrincipalSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.mappers.CategoryMapper;
import com.emu.apps.qcm.webmvc.rest.CategoryRestApi;
import com.emu.apps.shared.security.PrincipalUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class CategoryRestController implements CategoryRestApi {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryRestController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryMapper.modelToDto(categoryService.findById(id).orElse(null));
    }

    @Override
    public Iterable <CategoryDto> getCategories(Principal principal, @RequestParam("type") Category.Type type) {

        var principalSpecificationBuilder = new PrincipalSpecificationBuilder <Category>();
        principalSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));

        return categoryMapper.modelsToDtos(categoryService.findCategories(principalSpecificationBuilder.build()));
    }

    @Override
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto) {
        var category = categoryMapper.dtoToModel(categoryDto);
        return categoryMapper.modelToDto(categoryService.saveCategory(category));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
