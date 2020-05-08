package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.business.QuestionDelegate;
import com.emu.apps.qcm.webmvc.rest.QuestionRestApi;
import com.emu.apps.shared.metrics.Timer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class QuestionRestController implements QuestionRestApi {

    private QuestionDelegate questionDelegate;

    public QuestionRestController(QuestionDelegate questionDelegate) {
        this.questionDelegate = questionDelegate;
    }


    @Override
    @Timer
    public Iterable <QuestionTagsDto> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                   @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                                   Pageable pageable, Principal principal) {

        return questionDelegate.getQuestions(tagIds, questionnaireIds, pageable, principal);
    }


    @Override
    public QuestionDto getQuestionById(@PathVariable("id") long id) {
        return questionDelegate.getQuestionById(id);
    }

    @Override
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        return questionDelegate.updateQuestion(questionDto, principal);
    }

    @Override
    public QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal) {

        return questionDelegate.saveQuestion(questionDto, principal);

    }

    @Override
    public ResponseEntity <Question> deleteQuestionById(@PathVariable("id") long id) {
        questionDelegate.deleteQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
