package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
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

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PROTECTED_QUESTIONS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_QUESTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController {

    private final QuestionServicePort questionServicePort;

    public QuestionRestController(QuestionServicePort questionServicePort) {
        this.questionServicePort = questionServicePort;
    }

    @GetMapping
    @Timer
    public Iterable <QuestionTagsDto> getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                   @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                   Pageable pageable, Principal principal) {

        return questionServicePort.getQuestions(tagUuid, questionnaireUuid, pageable, principal);
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#uuid")
    @ResponseBody
    public QuestionDto getQuestionById(@PathVariable("uuid") String uuid) {
        return questionServicePort.getQuestionByUuId(uuid);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    @ResponseBody
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        return questionServicePort.updateQuestion(questionDto, principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionDto saveQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal) {

        return questionServicePort.saveQuestion(questionDto, principal);

    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    public ResponseEntity <Question> deleteQuestionById(@PathVariable("uuid") String uuid) {
        questionServicePort.deleteQuestionByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
