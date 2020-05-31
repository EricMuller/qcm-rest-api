package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.QuestionnaireService;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
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

import static com.emu.apps.qcm.webmvc.rest.RestMappings.QUESTIONNAIRES;


@RestController
@Profile("webmvc")
@RequestMapping(value = QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionnaireRestController {

    private final QuestionnaireService questionnaireService;

    public QuestionnaireRestController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping(value = "/{id}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        return questionnaireService.getQuestionnaireById(id);
    }


    @DeleteMapping(value = "/{id}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    public ResponseEntity <QuestionnaireDto> deleteQuestionnaireById(@PathVariable("id") long id) {
        questionnaireService.deleteQuestionnaireById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    @ResponseBody
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return questionnaireService.updateQuestionnaire(questionnaireDto, principal);
    }

    @PostMapping()
    @ResponseBody
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return questionnaireService.saveQuestionnaire(questionnaireDto, principal);
    }

    @GetMapping(value = "/{id:[\\d]+}/questions")
    @ResponseBody
    public Page <QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable) {
        return questionnaireService.getQuestionsByQuestionnaireId(id, pageable);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                     Pageable pageable, Principal principal) {
        return questionnaireService.getQuestionnaires(tagIds, pageable, principal);
    }

    @PutMapping(value = "/{id}/questions")
    @ResponseBody
    public QuestionDto addQuestion(@PathVariable("id") long id, @RequestBody QuestionDto questionDto) {

        // todo send QuestionnaireQuestionDto
        return questionnaireService.addQuestion(id, questionDto, null);
    }

}
