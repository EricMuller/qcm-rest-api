package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.shared.application.ApplicationService;
import com.emu.apps.shared.events.EventBus;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.emu.apps.qcm.application.events.AppEvents.QCM_CREATED;
import static java.util.Map.of;

@Service
public class QuestionnaireCatalog implements ApplicationService {


    private final QuestionnaireRepository questionnaireRepository;

    private final EventBus eventBus;

    public QuestionnaireCatalog(QuestionnaireRepository questionnaireRepository, @Qualifier("SimpleEventBus") EventBus eventBus) {
        this.questionnaireRepository = questionnaireRepository;
        this.eventBus = eventBus;
    }

    public QuestionnaireQuestion addQuestion(QuestionnaireId questionnaireId, QuestionId questionId, Optional <Integer> position, PrincipalId principal) {

        var questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException(questionId.toUuid(), MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));


        return questionnaireRepository.addQuestion(questionnaire.getId(), questionId, position, principal);
    }

    public Optional <Questionnaire> getQuestionnaireById(QuestionnaireId questionnaireId) {

        return questionnaireRepository.getQuestionnaireById(questionnaireId);
    }

    public ResponseEntity <Questionnaire> deleteQuestionnaireById(QuestionnaireId questionnaireId) {

        return questionnaireRepository.deleteQuestionnaireById(questionnaireId);
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

        return questionnaireRepository.getQuestionsByQuestionnaireId(questionnaireId, pageable);
    }

    public QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        return questionnaireRepository.getQuestion(questionnaireId, questionId);
    }

    public Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal) {
        return questionnaireRepository.getQuestionnaires(tagUuid, pageable, principal);
    }

    public void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        questionnaireRepository.deleteQuestion(questionnaireId, questionId);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

}
