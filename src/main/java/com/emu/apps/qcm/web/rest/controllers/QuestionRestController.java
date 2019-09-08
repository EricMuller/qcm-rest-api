package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.qcm.web.rest.QuestionRestApi;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionTagMapper;
import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.shared.web.rest.exceptions.utils.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class QuestionRestController implements QuestionRestApi {

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    private final QuestionTagService questionTagService;

    private final QuestionTagMapper questionTagMapper;

    @Autowired
    public QuestionRestController(QuestionService questionService, QuestionMapper questionMapper,
                                  QuestionTagService questionTagService, QuestionTagMapper questionTagMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.questionTagService = questionTagService;
        this.questionTagMapper = questionTagMapper;
    }

    @Override
    @Timer
    public Iterable<QuestionTagsDto> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                  @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                                  Pageable pageable, Principal principal)  {

        QuestionSpecificationBuilder questionSpecificationBuilder = new QuestionSpecificationBuilder();

        questionSpecificationBuilder.setPrincipal(principal.getName());
        questionSpecificationBuilder.setQuestionnaireIds(questionnaireIds);
        questionSpecificationBuilder.setTagIds(tagIds);

        return questionMapper.pageToPageTagDto(questionService.findAllByPage(questionSpecificationBuilder.build(), pageable));
    }


    @Override
    public QuestionDto getQuestionById(@PathVariable("id") long id) {
        return questionMapper.modelToDto(questionService.findById(id).orElse(null));
    }

    @Override
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        Question question = questionService.findById(questionDto.getId()).orElse(null);

        question = questionService.saveQuestion(questionMapper.dtoToModel(question, questionDto));

        Iterable<QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);
    }

    @Override
    public QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal) {

        Question question = questionService.saveQuestion(questionMapper.dtoToModel(questionDto));

        Iterable<QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);

    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity<MessageDto> handleAllException(Exception e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Question> deleteQuestionnaireById(@PathVariable("id") long id) {
        Optional<Question> questionOptional = questionService.findById(id);
        ExceptionUtil.assertIsPresent(questionOptional, String.valueOf(id));
        questionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
