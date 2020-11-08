package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Questionnaire;
import com.emu.apps.qcm.api.models.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.ports.QuestionnaireBusinessPort;
import com.emu.apps.qcm.spi.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
public class QuestionnaireRestController {

    private final QuestionnaireBusinessPort questionnaireServicePort;

    public QuestionnaireRestController(QuestionnaireBusinessPort questionnaireServicePort) {
        this.questionnaireServicePort = questionnaireServicePort;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public Questionnaire getQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getQuestionnaireByUuid(uuid);
    }


    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <Questionnaire> deleteQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        questionnaireServicePort.deleteQuestionnaireByUuid(uuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.uuid")
    @Timer
    @ResponseBody
    public Questionnaire updateQuestionnaire(@RequestBody Questionnaire questionnaireDto) {
        return questionnaireServicePort.updateQuestionnaire(questionnaireDto, AuthentificationContextHolder.getUser());
    }

    @PostMapping
    @ResponseBody
    public Questionnaire saveQuestionnaire(@RequestBody Questionnaire questionnaireDto) {
        return questionnaireServicePort.saveQuestionnaire(questionnaireDto, AuthentificationContextHolder.getUser());
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(@PathVariable("uuid") String uuid,
                                                                        @Parameter(hidden = true)
                                                                        @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

        return questionnaireServicePort.getQuestionsByQuestionnaireUuid(uuid, pageable);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <Questionnaire> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                  @Parameter(hidden = true)
                                                  @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireServicePort.getQuestionnaires(tagUuid, pageable, AuthentificationContextHolder.getUser());
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public Question addQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid, @RequestBody Question questionDto) {

        // send QuestionnaireQuestionDto
        return questionnaireServicePort.addQuestion(uuid, questionDto, Optional.empty(), AuthentificationContextHolder.getUser());
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireServicePort.deleteQuestion(uuid, questionUuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

}