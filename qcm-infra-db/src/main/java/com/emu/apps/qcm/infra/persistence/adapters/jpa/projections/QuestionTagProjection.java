package com.emu.apps.qcm.infra.persistence.adapters.jpa.projections;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTagEntity> getQuestionTags();

}
