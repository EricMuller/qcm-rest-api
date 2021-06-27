package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.CategoryRepository;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.controllers.secured.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.MessageResource;
import com.emu.apps.qcm.rest.controllers.secured.openui.CategoryView;
import com.emu.apps.shared.exceptions.FunctionnalException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PROTECTED_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_API + CATEGORIES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Category")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public CategoryRestController(CategoryRepository categoryRepository, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.categoryRepository = categoryRepository;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CategoryResource getCategoryByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourceMapper.categoryToResources(categoryRepository.getCategoryByUuid(uuid));
    }

    @GetMapping
    @ResponseBody
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    public Iterable <CategoryResource> getCategoriesByType(@RequestParam("type") String typeCategory) throws FunctionnalException {

        return questionnaireResourceMapper.categoriesToResources(
                categoryRepository.getCategories(new PrincipalId(getPrincipal()), typeCategory));
    }

    @PostMapping
    @ResponseBody
    public CategoryResource saveCategory(
            @JsonView(CategoryView.Create.class) @RequestBody CategoryResource categoryResource) {
        var category = questionnaireResourceMapper.categoryToModel(categoryResource);
        return questionnaireResourceMapper.categoryToResources(
                categoryRepository.saveCategory(category, new PrincipalId(getPrincipal())));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResource> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageResource(MessageResource.ERROR, e.getMessage()), BAD_REQUEST);
    }

}
