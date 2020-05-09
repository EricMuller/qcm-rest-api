package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.business.QuestionnaireDelegate;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
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

import static com.emu.apps.qcm.webmvc.rest.RestMapping.QUESTIONNAIRES;


@RestController
@Profile("webmvc")
@RequestMapping(value = QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionnaireRestController {

    private final QuestionnaireDelegate delegate;


    public QuestionnaireRestController(QuestionnaireDelegate questionnaireFacade) {
        this.delegate = questionnaireFacade;
    }

    @GetMapping(value = "/{id}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        return delegate.getQuestionnaireById(id);
    }

    @DeleteMapping(value = "/{id}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        delegate.deleteQuestionnaireById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    @ResponseBody
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return delegate.updateQuestionnaire(questionnaireDto, principal);
    }

    @PostMapping()
    @ResponseBody
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return delegate.saveQuestionnaire(questionnaireDto, principal);
    }

    @GetMapping(value = "/{id:[\\d]+}/questions")
    @ResponseBody
//    public Page<QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable) {
    public Page <QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable) {
        return delegate.getQuestionsByQuestionnaireId(id, pageable);
    }

    @GetMapping(produces = "application/json")
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                     Pageable pageable, Principal principal) {
        return delegate.getQuestionnaires(tagIds, pageable, principal);
    }

    @PutMapping(value = "/{id}/questions")
    @ResponseBody
    public QuestionDto updateQuestionnaire(@PathVariable("id") long id, @RequestBody QuestionDto questionDto) {
        return delegate.updateQuestionnaire(id, questionDto);
    }

}
