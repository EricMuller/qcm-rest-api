package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuery;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.shared.application.ApplicationService;
import com.emu.apps.shared.events.EventBus;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.emu.apps.qcm.application.events.AppEvents.QCM_CREATED;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static java.util.Map.of;

@Service
public class QuestionnaireCatalog implements ApplicationService {

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireQuery questionnaireQuery;

    private final EventBus eventBus;

    public QuestionnaireCatalog(QuestionnaireRepository questionnaireRepository, QuestionnaireQuery questionnaireQuery, @Qualifier("SimpleEventBus") EventBus eventBus) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireQuery = questionnaireQuery;
        this.eventBus = eventBus;
    }

    public QuestionnaireQuestion addQuestion(QuestionnaireId questionnaireId, QuestionId questionId, Optional <Integer> position, PrincipalId principal) {

        if (!questionnaireRepository.existsOfId(questionnaireId)) {
            throw new I18nedNotFoundException(UNKNOWN_UUID_QUESTIONNAIRE, questionnaireId.toUuid());
        }
        return questionnaireRepository.addQuestion(questionnaireId, questionId, position, principal);
    }

    public Optional <Questionnaire> getQuestionnaireById(QuestionnaireId questionnaireId) {

        return questionnaireRepository.getQuestionnaireOfId(questionnaireId);
    }

    public ResponseEntity <Questionnaire> deleteQuestionnaireById(QuestionnaireId questionnaireId) {

        return questionnaireRepository.deleteQuestionnaireOfId(questionnaireId);
    }

    public Questionnaire updateQuestionnaire(Questionnaire questionnaire, PrincipalId principal) {

        return questionnaireRepository.updateQuestionnaire(questionnaire, principal);
    }


    public Questionnaire saveQuestionnaire(Questionnaire questionnaire, PrincipalId principal) {

        var createdQuestionnaire = questionnaireRepository.saveQuestionnaire(questionnaire, principal);

        this.publishEvent(createEvent(QCM_CREATED, of("questionnaire_id", createdQuestionnaire.getId().toUuid())));

        return createdQuestionnaire;
    }

    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireId(QuestionnaireId questionnaireId, Pageable pageable) {

        return questionnaireRepository.getQuestionsByQuestionnaireOfId(questionnaireId, pageable);
    }

    public QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        return questionnaireRepository.getQuestion(questionnaireId, questionId);
    }

    public Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal) {
        return questionnaireQuery.getQuestionnaires(tagUuid, pageable, principal);
    }

    public void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        questionnaireRepository.deleteQuestion(questionnaireId, questionId);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

}
