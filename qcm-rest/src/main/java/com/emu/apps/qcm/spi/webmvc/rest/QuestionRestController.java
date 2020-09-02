package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.dtos.MessageDto;
import com.emu.apps.qcm.api.dtos.question.QuestionPatchDto;
import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.question.QuestionTags;
import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.spi.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.QUESTIONS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Question")
public class QuestionRestController {

    private final QuestionServicePort questionServicePort;

    public QuestionRestController(QuestionServicePort questionServicePort) {
        this.questionServicePort = questionServicePort;
    }

    @GetMapping
    @Timer
    public Iterable <QuestionTags> getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                Pageable pageable) {

        return questionServicePort.getQuestions(tagUuid, questionnaireUuid, pageable, AuthentificationContextHolder.getUser());
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#uuid")
    @ResponseBody
    public Question getQuestionByUuid(@PathVariable("uuid") String uuid) {
        return questionServicePort.getQuestionByUuId(uuid);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    @ResponseBody
    public Question updateQuestion(@RequestBody @Valid Question questionDto) {
        return questionServicePort.updateQuestion(questionDto, AuthentificationContextHolder.getUser());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question saveQuestion(@RequestBody @Valid Question questionDto) {
        return questionServicePort.saveQuestion(questionDto, AuthentificationContextHolder.getUser());
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionServicePort.deleteQuestionByUuid(uuid);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public Question patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionPatchDto patchDto) {
        Question dto = questionServicePort.getQuestionByUuId(uuid);
        dto.setStatus(patchDto.getStatus());
        return questionServicePort.saveQuestion(dto, AuthentificationContextHolder.getUser());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
