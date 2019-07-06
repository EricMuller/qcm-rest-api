package com.emu.apps.qcm.services.jpa.projections;

import afu.org.checkerframework.checker.igj.qual.Immutable;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;

import java.util.Set;

@Immutable
public interface QuestionTagProjection extends QuestionProjection {

    Set<QuestionTag> getQuestionTags();

}
