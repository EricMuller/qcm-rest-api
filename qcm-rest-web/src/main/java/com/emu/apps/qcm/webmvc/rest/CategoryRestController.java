package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.CategoryServicePort;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.domain.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PROTECTED_CATEGORIES;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

    private final CategoryServicePort categoryServicePort;

    public CategoryRestController(CategoryServicePort categoryServicePort) {
        this.categoryServicePort = categoryServicePort;
    }

    @GetMapping(value = "{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CategoryDto getCategory(@PathVariable("uuid") String uuid) {
        return categoryServicePort.getCategoryByUuid(uuid);
    }

    @GetMapping()
    @ResponseBody
    public Iterable <CategoryDto> getCategories(Principal principal, @RequestParam("type") Type type) throws FunctionnalException {
        return categoryServicePort.getCategories(principal, type);
    }

    @PostMapping()
    @ResponseBody
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        return categoryServicePort.saveCategory(categoryDto, principal);
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
