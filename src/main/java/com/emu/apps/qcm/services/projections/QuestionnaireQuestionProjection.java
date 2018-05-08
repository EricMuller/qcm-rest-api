package com.emu.apps.qcm.services.projections;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import org.hibernate.annotations.Immutable;

@Immutable
public interface QuestionnaireQuestionProjection extends QuestionResponseProjection {


    public Questionnaire getQuestionnaire();


}
