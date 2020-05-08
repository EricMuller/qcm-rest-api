package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.business.QuestionnaireDelegate;
import com.emu.apps.qcm.webmvc.rest.QuestionnaireRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@Profile("webmvc")
public class QuestionnaireRestController implements QuestionnaireRestApi {

    private final QuestionnaireDelegate delegate;


    public QuestionnaireRestController(QuestionnaireDelegate questionnaireFacade) {
        this.delegate = questionnaireFacade;
    }

    @Override
    public QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id) {
        return delegate.getQuestionnaireById(id);
    }

    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id) {
        delegate.deleteQuestionnaireById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @Override
    public QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return delegate.updateQuestionnaire(questionnaireDto, principal);
    }

    @Override
    public QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal) {
        return delegate.saveQuestionnaire(questionnaireDto, principal);
    }

    @Override
//    public Page<QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") @ApiParam(value = "ID of the Questionnaire") long id, Pageable pageable) {
    public Page <QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable) {
        return delegate.getQuestionsByQuestionnaireId(id, pageable);
    }

    @Override
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                     Pageable pageable, Principal principal) {
        return delegate.getQuestionnaires(tagIds, pageable, principal);
    }

    @Override
    public QuestionDto updateQuestionnaire(@PathVariable("id") long id, @RequestBody QuestionDto questionDto) {
        return delegate.updateQuestionnaire(id, questionDto);
    }

}
