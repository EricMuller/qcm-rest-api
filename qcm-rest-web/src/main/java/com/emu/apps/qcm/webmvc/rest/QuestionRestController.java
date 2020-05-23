package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.domain.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.QUESTIONS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = QUESTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController {

    private final QuestionService questionService;

    public QuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    @Timer
    public Iterable <QuestionTagsDto> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                   @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                                   Pageable pageable, Principal principal) {

        return questionService.getQuestions(tagIds, questionnaireIds, pageable, principal);
    }


    @GetMapping(value = "/{id}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#id")
    @ResponseBody
    public QuestionDto getQuestionById(@PathVariable("id") long id) {
        return questionService.getQuestionById(id);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    @ResponseBody
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        return questionService.updateQuestion(questionDto, principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionDto saveQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        return questionService.saveQuestion(questionDto, principal);

    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    public ResponseEntity <Question> deleteQuestionById(@PathVariable("id") long id) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
