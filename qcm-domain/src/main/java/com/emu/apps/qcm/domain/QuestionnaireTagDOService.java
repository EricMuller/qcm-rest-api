package com.emu.apps.qcm.domain;

import com.emu.apps.qcm.domain.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.domain.entity.tags.QuestionnaireTag;

import java.security.Principal;

public interface QuestionnaireTagDOService {

    QuestionnaireTag saveQuestionnaireTag(QuestionnaireTag questionnaireTag);

    Questionnaire saveQuestionnaireTags(long questionnaireId, Iterable <QuestionnaireTag> questionnaireTag, Principal principal);

}
