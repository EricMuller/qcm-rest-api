package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.mappers.CategoryMapper;
import com.emu.apps.qcm.web.mappers.QuestionCategoryMapper;
import com.emu.apps.qcm.web.mappers.QuestionnaireCategoryMapper;
import com.emu.apps.qcm.webmvc.rest.CategoryRestApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class CategoryRestController implements CategoryRestApi {

    private final CategoryService categoryService;

    private final QuestionCategoryMapper questionCategoryMapper;

    private final QuestionnaireCategoryMapper questionnaireCategoryMapper;

    private final CategoryMapper categoryMapper;

    public CategoryRestController(CategoryService categoryService, QuestionCategoryMapper questionCategoryMapper, QuestionnaireCategoryMapper questionnaireCategoryMapper, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.questionCategoryMapper = questionCategoryMapper;
        this.questionnaireCategoryMapper = questionnaireCategoryMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryMapper.modelToDto(categoryService.findById(id).orElse(null));
    }

    @Override
    public Iterable <CategoryDto> getQuestionsCategories() {
        return questionCategoryMapper.modelsToDtos(categoryService.findQuestionCategories());
    }

    @Override
    public Iterable <CategoryDto> getQuestionnairesCategories() {
        return questionnaireCategoryMapper.modelsToDtos(categoryService.findQuestionnairesCategories());
    }

    @Override
    public CategoryDto saveQuestionCategory(@RequestBody CategoryDto categoryDto) {
        var category = questionCategoryMapper.dtoToModel(categoryDto);
        return questionCategoryMapper.modelToDto(categoryService.saveQuestionCategory(category));
    }

    @Override
    public CategoryDto saveQuestionnaireCategory(@RequestBody CategoryDto categoryDto) {
        var category = questionnaireCategoryMapper.dtoToModel(categoryDto);
        return categoryMapper.modelToDto(categoryService.saveQuestionnaireCategory(category));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(MessageDto.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
