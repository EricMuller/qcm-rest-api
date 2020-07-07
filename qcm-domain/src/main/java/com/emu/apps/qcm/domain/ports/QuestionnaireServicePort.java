package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.models.QuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireServicePort {
    QuestionnaireDto getQuestionnaireByUuid(String uuid);

    ResponseEntity <QuestionnaireDto> deleteQuestionnaireByUuid(String uuid);

    Page <QuestionnaireQuestionDto> getQuestionsByQuestionnaireUuid(String uuid, Pageable pageable);

    QuestionDto addQuestion(String questionnaireUuid, QuestionDto questionDto, Optional <Long> position);

    List <QuestionDto> addQuestions(String uuid, Collection <QuestionDto> questionDtos);

    QuestionnaireDto updateQuestionnaire(QuestionnaireDto questionnaireDto, String principal);

    QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, String principal);

    Page <QuestionnaireDto> getQuestionnaires(String[] tagUuid, Pageable pageable, String principal);

    void  deleteQuestion(String questionnaireUuid,  String questionUuid) ;

}
