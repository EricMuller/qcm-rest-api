package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireServicePort {
    QuestionnaireDto getQuestionnaireByUuid(String uuid);

    ResponseEntity <QuestionnaireDto> deleteQuestionnaireByUuid(String uuid);

    Page <QuestionDto> getQuestionsByQuestionnaireUuid(String uuid, Pageable pageable);

    QuestionDto addQuestion(String questionnaireUuid, QuestionDto questionDto, Optional <Long> position);

    List <QuestionDto> addQuestions(String uuid, Collection <QuestionDto> questionDtos);

    QuestionnaireDto updateQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal);

    QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal);

    Page <QuestionnaireDto> getQuestionnaires(String[] tagUuid, Pageable pageable, Principal principal);

    Page <QuestionnaireDto> getPublicQuestionnaires(String[] tagUuid, Pageable pageable, String principal);

}
