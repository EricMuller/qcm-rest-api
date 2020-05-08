package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.services.entity.category.Type;
import com.emu.apps.qcm.services.exceptions.FunctionnalException;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.business.CategoryDelegate;
import com.emu.apps.qcm.webmvc.rest.CategoryRestApi;
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

    private CategoryDelegate categoryDelegate;

    public CategoryRestController(CategoryDelegate categoryDelegate) {
        this.categoryDelegate = categoryDelegate;
    }

    @Override
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryDelegate.getCategory(id);
    }

    @Override
    public Iterable <CategoryDto> getCategories(Principal principal, @RequestParam("type") Type type) throws FunctionnalException {
        return categoryDelegate.getCategories(principal, type);
    }

    @Override
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        return categoryDelegate.saveCategory(categoryDto,principal);
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
