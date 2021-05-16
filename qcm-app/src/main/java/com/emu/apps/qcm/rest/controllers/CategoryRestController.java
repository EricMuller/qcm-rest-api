package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.category.CategoryRepository;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.CategoryResources;
import com.emu.apps.qcm.rest.controllers.resources.MessageResources;
import com.emu.apps.shared.exceptions.FunctionnalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.CATEGORIES;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
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

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;

    public CategoryRestController(CategoryRepository categoryRepository, QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.categoryRepository = categoryRepository;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CategoryResources getCategoryByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourcesMapper.categoryToResources(categoryRepository.getCategoryByUuid(uuid));
    }

    @GetMapping
    @ResponseBody
    public Iterable <CategoryResources> getCategoriesByType(@RequestParam("type") String typeCategory) throws FunctionnalException {

        return questionnaireResourcesMapper.categoriesToResources(
                categoryRepository.getCategories(new PrincipalId(getPrincipal()), typeCategory));
    }

    @PostMapping
    @ResponseBody
    public CategoryResources saveCategory(@RequestBody CategoryResources categoryResources) {
        var category = questionnaireResourcesMapper.categoryToModel(categoryResources);
        return questionnaireResourcesMapper.categoryToResources(
                categoryRepository.saveCategory(category, new PrincipalId(getPrincipal())));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResources> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageResources(MessageResources.ERROR, e.getMessage()), BAD_REQUEST);
    }

}
