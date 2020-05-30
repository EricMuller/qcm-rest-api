package com.emu.apps.qcm.infrastructure.adapters.jpa.projections;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTag> getQuestionTags();

}
