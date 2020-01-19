package com.emu.apps.qcm.webmvc.services.jpa.projections;

import com.emu.apps.qcm.webmvc.services.jpa.entity.tags.QuestionTag;

import java.util.Set;


public interface QuestionTagProjection extends QuestionProjection {

    Set <QuestionTag> getQuestionTags();

}
