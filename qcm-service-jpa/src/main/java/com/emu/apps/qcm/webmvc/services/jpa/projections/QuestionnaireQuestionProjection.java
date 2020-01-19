package com.emu.apps.qcm.webmvc.services.jpa.projections;

import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.Questionnaire;
import org.hibernate.annotations.Immutable;

@Immutable
public interface QuestionnaireQuestionProjection extends QuestionResponseProjection {


    Questionnaire getQuestionnaire();


}
