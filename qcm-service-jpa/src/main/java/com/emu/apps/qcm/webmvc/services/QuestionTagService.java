package com.emu.apps.qcm.webmvc.services;

import com.emu.apps.qcm.webmvc.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.webmvc.services.jpa.entity.tags.QuestionTag;

import java.security.Principal;

public interface QuestionTagService {

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Question saveQuestionTags(long questionId, Iterable<QuestionTag> questionTag,Principal principal);

}
