package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Questionnaire;
import com.emu.apps.qcm.domain.models.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.infra.persistence.exceptions.EntityNotFoundException;
import com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.infra.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
public class QuestionnaireRestController {

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireRestController(QuestionnaireRepository questionnaireServicePort) {
        this.questionnaireRepository = questionnaireServicePort;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public Questionnaire getQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireRepository.getQuestionnaireByUuid(uuid)
                .orElseThrow(()-> new EntityNotFoundException(uuid, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));
    }

    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <Questionnaire> deleteQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        questionnaireRepository.deleteQuestionnaireByUuid(uuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.uuid")
    @Timer
    @ResponseBody
    public Questionnaire updateQuestionnaire(@RequestBody Questionnaire questionnaireDto) {
        return questionnaireRepository.updateQuestionnaire(questionnaireDto, AuthentificationContextHolder.getPrincipal());
    }

    @PostMapping
    @ResponseBody
    public Questionnaire saveQuestionnaire(@RequestBody Questionnaire questionnaireDto) {
        return questionnaireRepository.saveQuestionnaire(questionnaireDto, AuthentificationContextHolder.getPrincipal());
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(@PathVariable("uuid") String uuid,
                                                                        @Parameter(hidden = true)
                                                                        @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

        LOGGER.warn("getQuestionsByQuestionnaireUuid");
        return questionnaireRepository.getQuestionsByQuestionnaireUuid(uuid, pageable);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <Questionnaire> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                  @Parameter(hidden = true)
                                                  @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireRepository.getQuestionnaires(tagUuid, pageable, AuthentificationContextHolder.getPrincipal());
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public Question addQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid, @RequestBody Question question) {

        // send QuestionnaireQuestionDto
        return questionnaireRepository.addQuestion(uuid, question, Optional.empty(), AuthentificationContextHolder.getPrincipal());
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireRepository.deleteQuestion(uuid, questionUuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

}
