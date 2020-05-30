package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionnaireTag;

import java.security.Principal;

public interface QuestionnaireTagDOService {

    QuestionnaireTag saveQuestionnaireTag(QuestionnaireTag questionnaireTag);

    Questionnaire saveQuestionnaireTags(long questionnaireId, Iterable <QuestionnaireTag> questionnaireTag, Principal principal);

}
