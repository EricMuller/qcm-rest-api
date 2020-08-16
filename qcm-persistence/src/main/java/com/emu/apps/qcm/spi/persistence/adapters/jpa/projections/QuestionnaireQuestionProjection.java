package com.emu.apps.qcm.spi.persistence.adapters.jpa.projections;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import org.hibernate.annotations.Immutable;

@Immutable
public interface QuestionnaireQuestionProjection extends QuestionResponseProjection {


    QuestionnaireEntity getQuestionnaire();


}
