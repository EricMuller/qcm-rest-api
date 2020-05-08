package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;

import java.security.Principal;

public interface QuestionTagService {

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Question saveQuestionTags(long questionId, Iterable <QuestionTag> questionTag, Principal principal);

}
