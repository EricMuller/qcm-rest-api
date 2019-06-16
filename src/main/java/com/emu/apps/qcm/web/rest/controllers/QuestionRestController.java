package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.repositories.specifications.question.QuestionSpecification;
import com.emu.apps.qcm.web.rest.QuestionRestApi;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.rest.dtos.utils.FilterUtil;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionTagMapper;
import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.shared.web.rest.exceptions.utils.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class QuestionRestController implements QuestionRestApi {

    protected static final Logger logger = LoggerFactory.getLogger(QuestionRestController.class);

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    private final QuestionTagService questionTagService;

    private final QuestionTagMapper questionTagMapper;

    private final QuestionSpecification questionSpecification;

    private final FilterUtil dtoUtil;

    @Autowired
    public QuestionRestController(QuestionService questionService, QuestionMapper questionMapper, QuestionTagService questionTagService,
                                  QuestionTagMapper questionTagMapper, QuestionSpecification questionSpecification, FilterUtil dtoUtil) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.questionTagService = questionTagService;
        this.questionTagMapper = questionTagMapper;
        this.questionSpecification = questionSpecification;
        this.dtoUtil = dtoUtil;
    }


    @Override
    @Timer
    public Iterable <QuestionTagsDto> getQuestionsWithFilters(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException {
        FilterDto[] filterDtos = dtoUtil.stringToFilterDtos(filterString);
        return questionMapper.pageToPageTagDto(questionService.findAllByPage(questionSpecification.getSpecifications(filterDtos, principal), pageable));
    }


    @Override
    public QuestionDto getQuestionById(@PathVariable("id") long id) {
        return questionMapper.modelToDto(questionService.findById(id).orElse(null));
    }

    @Override
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        Question question = questionService.findById(questionDto.getId()).orElse(null);

        question = questionService.saveQuestion(questionMapper.dtoToModel(question, questionDto));

        Iterable <QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);
    }

    @Override
    public QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal) {

        Question question = questionService.saveQuestion(questionMapper.dtoToModel(questionDto));

        Iterable <QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);

    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return new ResponseEntity <>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity <Question> deleteQuestionnaireById(@PathVariable("id") long id) {
        Optional <Question> questionOptional = questionService.findById(id);
        ExceptionUtil.assertFound(questionOptional, String.valueOf(id));
        questionService.deleteById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

}
