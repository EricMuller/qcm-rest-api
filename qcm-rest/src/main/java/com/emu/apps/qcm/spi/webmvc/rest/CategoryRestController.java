package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.domain.ports.CategoryServicePort;
import com.emu.apps.qcm.api.dtos.MessageDto;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.CATEGORIES;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Category")
public class CategoryRestController {

    private final CategoryServicePort categoryServicePort;

    public CategoryRestController(CategoryServicePort categoryServicePort) {
        this.categoryServicePort = categoryServicePort;
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Category getCategoryByUuid(@PathVariable("uuid") String uuid) {
        return categoryServicePort.getCategoryByUuid(uuid);
    }

    @GetMapping
    @ResponseBody
    public Iterable <Category> getCategoriesByType(@RequestParam("type") Type type) throws FunctionnalException {
        return categoryServicePort.getCategories(AuthentificationContextHolder.getUser(), type);
    }

    @PostMapping
    @ResponseBody
    public Category saveCategory(@RequestBody Category categoryDto) throws FunctionnalException {
        return categoryServicePort.saveCategory(categoryDto, AuthentificationContextHolder.getUser());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
