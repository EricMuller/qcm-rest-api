package com.emu.apps.qcm.domain.jpa.projections;

import com.emu.apps.qcm.domain.entity.tags.QuestionTag;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTag> getQuestionTags();

}
