package com.emu.apps.qcm.infra.webmvc.rest;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.domain.repositories.CategoryRepository;
import com.emu.apps.qcm.infra.webmvc.rest.dtos.MessageDto;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infra.persistence.exceptions.FunctionnalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.CATEGORIES;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.dtos.MessageDto.ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + CATEGORIES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Category")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;

    public CategoryRestController(CategoryRepository categoryServicePort) {
        this.categoryRepository = categoryServicePort;
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Category getCategoryByUuid(@PathVariable("uuid") String uuid) {
        return categoryRepository.getCategoryByUuid(uuid);
    }

    @GetMapping
    @ResponseBody
    public Iterable <Category> getCategoriesByType(@RequestParam("type") Type type) throws FunctionnalException {
        return categoryRepository.getCategories(AuthentificationContextHolder.getPrincipal(), type);
    }

    @PostMapping
    @ResponseBody
    public Category saveCategory(@RequestBody Category categoryDto)  {
        return categoryRepository.saveCategory(categoryDto, AuthentificationContextHolder.getPrincipal());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(ERROR, e.getMessage()), BAD_REQUEST);
    }

}
