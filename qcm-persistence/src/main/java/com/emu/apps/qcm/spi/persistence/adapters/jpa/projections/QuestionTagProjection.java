package com.emu.apps.qcm.spi.persistence.adapters.jpa.projections;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTagEntity> getQuestionTags();

}
