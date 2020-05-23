package com.emu.apps.qcm.domain;

import com.emu.apps.qcm.domain.entity.questions.Question;
import com.emu.apps.qcm.domain.entity.tags.QuestionTag;

import java.security.Principal;

public interface QuestionTagDOService {

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Question saveQuestionTags(long questionId, Iterable <QuestionTag> questionTag, Principal principal);

}
