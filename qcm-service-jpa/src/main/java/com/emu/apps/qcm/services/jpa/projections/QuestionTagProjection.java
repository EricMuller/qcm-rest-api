package com.emu.apps.qcm.services.jpa.projections;

import com.emu.apps.qcm.services.entity.tags.QuestionTag;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTag> getQuestionTags();

}
