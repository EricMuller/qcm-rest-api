package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.dtos.MessageDto;
import com.emu.apps.qcm.dtos.question.QuestionPatchDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.question.QuestionTagsDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.UserContextHolder;
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

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.QUESTIONS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController {

    private final QuestionServicePort questionServicePort;

    public QuestionRestController(QuestionServicePort questionServicePort) {
        this.questionServicePort = questionServicePort;
    }

    @GetMapping
    @Timer
    public Iterable <QuestionTagsDto> getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                   @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                   Pageable pageable) {

        return questionServicePort.getQuestions(tagUuid, questionnaireUuid, pageable, UserContextHolder.getUser());
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#uuid")
    @ResponseBody
    public QuestionDto getQuestionByUuid(@PathVariable("uuid") String uuid) {
        return questionServicePort.getQuestionByUuId(uuid);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    @ResponseBody
    public QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto) {
        return questionServicePort.updateQuestion(questionDto, UserContextHolder.getUser());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionDto saveQuestion(@RequestBody @Valid QuestionDto questionDto) {
        return questionServicePort.saveQuestion(questionDto, UserContextHolder.getUser());
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public ResponseEntity <Question> deleteQuestionById(@PathVariable("uuid") String uuid) {
        questionServicePort.deleteQuestionByUuid(uuid);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public QuestionDto patchQuestion(@PathVariable("uuid") String uuid, @RequestBody QuestionPatchDto patchDto) {
        QuestionDto dto = questionServicePort.getQuestionByUuId(uuid);
        dto.setStatus(patchDto.getStatus());
        return questionServicePort.saveQuestion(dto, UserContextHolder.getUser());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
