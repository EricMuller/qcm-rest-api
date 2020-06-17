package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_QUESTIONNAIRES;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionnaireRestController {

    private final QuestionnaireServicePort questionnaireServicePort;

    public QuestionnaireRestController(QuestionnaireServicePort questionnaireServicePort) {
        this.questionnaireServicePort = questionnaireServicePort;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public QuestionnaireDto getQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getQuestionnaireByUuid(uuid);
    }


    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <QuestionnaireDto> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireServicePort.deleteQuestionnaireByUuid(uuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.uuid")
    @Timer
    @ResponseBody
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return questionnaireServicePort.updateQuestionnaire(questionnaireDto, principal);
    }

    @PostMapping()
    @ResponseBody
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return questionnaireServicePort.saveQuestionnaire(questionnaireDto, principal);
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}/questions")
    @ResponseBody
    public Page <QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid, Pageable pageable) {
        return questionnaireServicePort.getQuestionsByQuestionnaireUuid(uuid, pageable);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                     Pageable pageable, Principal principal) {
        return questionnaireServicePort.getQuestionnaires(tagUuid, pageable, principal);
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public QuestionDto addQuestion(@PathVariable("uuid") String uuid, @RequestBody QuestionDto questionDto) {

        // todo send QuestionnaireQuestionDto
        return questionnaireServicePort.addQuestion(uuid, questionDto, Optional.empty());
    }

}
