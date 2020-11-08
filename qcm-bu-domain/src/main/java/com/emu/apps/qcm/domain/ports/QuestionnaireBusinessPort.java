package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Questionnaire;
import com.emu.apps.qcm.api.models.QuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireBusinessPort {
    Questionnaire getQuestionnaireByUuid(String uuid);

    ResponseEntity <Questionnaire> deleteQuestionnaireByUuid(String uuid);

    Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(String uuid, Pageable pageable);

    Question addQuestion(String questionnaireUuid, Question questionDto, Optional <Integer> position, String principal);

    List <Question> addQuestions(String uuid, Collection <Question> questionDtos,String principal);

    Questionnaire updateQuestionnaire(Questionnaire questionnaireDto, String principal);

    Questionnaire saveQuestionnaire(Questionnaire questionnaireDto, String principal);

    Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, String principal);

    void deleteQuestion(String questionnaireUuid, String questionUuid);

    void activateQuestionnaire(String questionnaireUuid);

}
