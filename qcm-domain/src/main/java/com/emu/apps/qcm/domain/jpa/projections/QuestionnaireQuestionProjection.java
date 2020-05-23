package com.emu.apps.qcm.domain.jpa.projections;

import com.emu.apps.qcm.domain.entity.questionnaires.Questionnaire;
import org.hibernate.annotations.Immutable;

@Immutable
public interface QuestionnaireQuestionProjection extends QuestionResponseProjection {


    Questionnaire getQuestionnaire();


}
