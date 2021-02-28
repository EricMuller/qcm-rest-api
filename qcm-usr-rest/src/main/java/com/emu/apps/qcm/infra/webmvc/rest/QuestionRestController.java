package com.emu.apps.qcm.infra.webmvc.rest;

import com.emu.apps.qcm.domain.dtos.question.QuestionPatchDto;
import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Tag;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.domain.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.exceptions.EntityNotFoundException;
import com.emu.apps.qcm.infra.webmvc.rest.dtos.MessageDto;
import com.emu.apps.qcm.infra.webmvc.rest.validators.ValidUuid;
import com.emu.apps.shared.annotations.Timer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport.UNKNOWN_UUID_QUESTION;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.*;
import static com.emu.apps.qcm.infra.webmvc.rest.caches.CacheName.Names.QUESTION;
import static com.emu.apps.qcm.infra.webmvc.rest.dtos.MessageDto.ERROR;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONS, produces = APPLICATION_JSON_VALUE)
@io.swagger.v3.oas.annotations.tags.Tag(name = "Question")
@Validated
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

        return questionRepository.getQuestions(tagUuid, questionnaireUuid, pageable, getPrincipal());
    }

    @GetMapping(value = TAGS)
    @Timer
    @PageableAsQueryParam
    public Iterable <Tag> getTags(@Parameter(hidden = true) Pageable pageable) {

        return questionRepository.findAllQuestionTagByPage(pageable, getPrincipal());
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTION, key = "#uuid")
    @ResponseBody
    public Question getQuestionByUuid(@ValidUuid @PathVariable("uuid") String uuid) {
        return questionRepository.getQuestionByUuId(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTION));
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = QUESTION, condition = "#questionDto != null", key = "#questionDto.uuid")
    @ResponseBody
    public Question updateQuestion(@RequestBody @Valid Question questionDto) {
        return questionRepository.updateQuestion(questionDto, getPrincipal());
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question saveQuestion(@RequestBody @Valid Question questionDto) {
        return questionRepository.saveQuestion(questionDto, getPrincipal());
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")
    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionRepository.deleteQuestionByUuid(uuid);
        return noContent().build();
    }


    @PatchMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")
    public Question patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionPatchDto questionPatchDto) {
        var dto = questionRepository.getQuestionByUuId(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTION));

        dto.setStatus(questionPatchDto.getStatus());
        return questionRepository.saveQuestion(dto, getPrincipal());
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageDto> handleAllException(Exception e) {
        return badRequest().body(new MessageDto(ERROR, e.getMessage()));
    }


}
