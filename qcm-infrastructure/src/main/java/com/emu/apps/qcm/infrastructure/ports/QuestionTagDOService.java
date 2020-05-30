package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;

import java.security.Principal;

public interface QuestionTagDOService {

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Question saveQuestionTags(long questionId, Iterable <QuestionTag> questionTag, Principal principal);

}
