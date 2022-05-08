package com.emu.apps.qcm.rest.controllers.management;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.MpttCategoryRepository;
import com.emu.apps.qcm.rest.controllers.management.openui.CategoryView;
import com.emu.apps.qcm.rest.controllers.management.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.management.resources.MessageResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.shared.exceptions.FunctionnalException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static com.emu.apps.qcm.domain.model.category.TypeCategory.QUESTION;
import static com.emu.apps.qcm.domain.model.category.TypeCategory.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */

@RestController
@RequestMapping(value = MANAGEMENT_API + CATEGORIES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Category")
@Timed("categories")
public class CategoryRestController {

    private final MpttCategoryRepository mpttCategoryRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public CategoryRestController(MpttCategoryRepository mpttCategoryRepository, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.mpttCategoryRepository = mpttCategoryRepository;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed("categories.getCategoryByUuid")
    public CategoryResource getCategoryByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourceMapper.categoryToResources(mpttCategoryRepository.getCategoryByUuid(uuid));
    }

    @GetMapping
    @ResponseBody
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    @Timed("categories.getCategoriesByType")
    public Iterable <CategoryResource> getCategoriesByType(@RequestParam("type") String typeCategory) throws FunctionnalException {

        return questionnaireResourceMapper.categoriesToResources(
                mpttCategoryRepository.getCategories(PrincipalId.of(getPrincipal()), typeCategory));
    }

    @PostMapping
    @ResponseBody
    @Timed("categories.saveCategory")
    public CategoryResource saveCategory(
            @JsonView(CategoryView.Create.class) @RequestBody CategoryResource categoryResource) {
        var category = questionnaireResourceMapper.categoryToModel(categoryResource);
        return questionnaireResourceMapper.categoryToResources(
                mpttCategoryRepository.saveCategory(category,  PrincipalId.of(getPrincipal())));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResource> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageResource(MessageResource.ERROR, e.getMessage()), BAD_REQUEST);
    }


    @GetMapping(QUESTIONS)
    @ResponseBody
    @Operation(summary = "Get all Questions categories")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    @Timed("questions.getCategories")
    public Iterable <CategoryResource> getQuestionCategories() throws FunctionnalException {

        return questionnaireResourceMapper.categoriesToResources(
                mpttCategoryRepository.getCategories(PrincipalId.of(getPrincipal()), QUESTION.name()));
    }

    @GetMapping(QUESTIONNAIRES)
    @ResponseBody
    @Operation(summary = "Get all questionnaires categories")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResource.class))))
    @Timed("questions.getCategories")
    public Iterable <CategoryResource> getQuestionnairesCategories() throws FunctionnalException {

        return questionnaireResourceMapper.categoriesToResources(
                mpttCategoryRepository.getCategories(PrincipalId.of(getPrincipal()), QUESTIONNAIRE.name()));
    }

}
