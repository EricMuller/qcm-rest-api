package com.emu.apps.qcm.infra.webmvc.rest;

import com.emu.apps.qcm.infra.persistence.exceptions.EntityNotFoundException;
import com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.infra.webmvc.rest.dtos.MessageDto;
import com.emu.apps.qcm.domain.dtos.question.QuestionPatchDto;
import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.domain.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.QUESTIONS;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Question")
public class QuestionRestController {

    private final QuestionRepository questionRepository;

    public QuestionRestController(QuestionRepository questionServicePort) {
        this.questionRepository = questionServicePort;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Iterable <QuestionTags> getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                @Parameter(hidden = true)
                                                @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionRepository.getQuestions(tagUuid, questionnaireUuid, pageable, AuthentificationContextHolder.getPrincipal());
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#uuid")
    @ResponseBody
    public Question getQuestionByUuid(@PathVariable("uuid") String uuid) {
        return questionRepository.getQuestionByUuId(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, MessageSupport.UNKNOWN_UUID_QUESTION));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    @ResponseBody
    public Question updateQuestion(@RequestBody @Valid Question questionDto) {
        return questionRepository.updateQuestion(questionDto, AuthentificationContextHolder.getPrincipal());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question saveQuestion(@RequestBody @Valid Question questionDto) {
        return questionRepository.saveQuestion(questionDto, AuthentificationContextHolder.getPrincipal());
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionRepository.deleteQuestionByUuid(uuid);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#uuid != null", key = "#uuid")
    public Question patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionPatchDto patchDto) {
        var dto = questionRepository.getQuestionByUuId(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid,MessageSupport.UNKNOWN_UUID_QUESTION));

        dto.setStatus(patchDto.getStatus());
        return questionRepository.saveQuestion(dto, AuthentificationContextHolder.getPrincipal());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(MessageDto.ERROR, e.getMessage()));
    }

}
