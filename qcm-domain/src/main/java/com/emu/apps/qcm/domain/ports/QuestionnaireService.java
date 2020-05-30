package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public interface QuestionnaireService {
    QuestionnaireDto getQuestionnaireById(long id);

    ResponseEntity <Questionnaire> deleteQuestionnaireById(long id);

    QuestionnaireDto updateQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal);

    QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal);

    Page <QuestionDto> getQuestionsByQuestionnaireId(long id, Pageable pageable);

    Page <QuestionnaireDto> getQuestionnaires(Long[] tagIds, Pageable pageable, Principal principal);

    QuestionDto addQuestion(long id, QuestionDto questionDto, Long position);

    List <QuestionDto> addQuestions(long questionnaireId, Collection <QuestionDto> questionDtos);
}
