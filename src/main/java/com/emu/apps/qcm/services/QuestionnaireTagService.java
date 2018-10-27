package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;

import java.security.Principal;

public interface QuestionnaireTagService {

    QuestionnaireTag saveQuestionnaireTag(QuestionnaireTag questionnaireTag);

    Questionnaire saveQuestionnaireTags(long questionnaireId, Iterable<QuestionnaireTag> questionnaireTag, Principal principal);

}
