package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository {
    Optional <Questionnaire> getQuestionnaireById(QuestionnaireId questionnaireId);

    ResponseEntity <Questionnaire> deleteQuestionnaireById(QuestionnaireId questionnaireId);

    Page <QuestionnaireQuestion> getQuestionsByQuestionnaireId(QuestionnaireId questionnaireId, Pageable pageable);

    Iterable <QuestionnaireQuestion> getQuestionsByQuestionnaireId(QuestionnaireId questionnaireId);

    Question addQuestion(QuestionnaireId questionnaireId, Question question, Optional <Integer> position, PrincipalId principal);

    List <Question> addQuestions(QuestionnaireId questionnaireId, Collection <Question> questionDtos, PrincipalId principal);

    Questionnaire updateQuestionnaire(Questionnaire questionnaire, PrincipalId principal);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire, PrincipalId principal);

    Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal);

    void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId);

    QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId);

    void activateQuestionnaire(QuestionnaireId questionnaireId);

}
