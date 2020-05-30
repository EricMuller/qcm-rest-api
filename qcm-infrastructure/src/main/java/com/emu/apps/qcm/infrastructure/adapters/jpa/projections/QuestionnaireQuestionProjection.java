package com.emu.apps.qcm.infrastructure.adapters.jpa.projections;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import org.hibernate.annotations.Immutable;

@Immutable
public interface QuestionnaireQuestionProjection extends QuestionResponseProjection {


    Questionnaire getQuestionnaire();


}
