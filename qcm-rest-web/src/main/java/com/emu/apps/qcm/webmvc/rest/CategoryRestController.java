package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.CategoryService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.CATEGORIES;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@Profile("webmvc")
@RequestMapping(value = CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping()
    @ResponseBody
    public Iterable <CategoryDto> getCategories(Principal principal, @RequestParam("type") Type type) throws FunctionnalException {
        return categoryService.getCategories(principal, type);
    }

    @PostMapping()
    @ResponseBody
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        return categoryService.saveCategory(categoryDto, principal);
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
