package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionnaireCatalog {

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireCatalog(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    public QuestionnaireQuestion addQuestion(QuestionnaireId questionnaireId, QuestionId questionId, Optional <Integer> position, PrincipalId principal) {

        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException(questionId.toUuid(), MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));


        return questionnaireRepository.addQuestion(questionnaire.getId(), questionId, position, principal);
    }


}
