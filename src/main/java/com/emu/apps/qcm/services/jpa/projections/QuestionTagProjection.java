package com.emu.apps.qcm.services.jpa.projections;

import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Set;

@Immutable
public interface QuestionTagProjection extends QuestionProjection {

    Set<QuestionTag> getQuestionTags();

}
