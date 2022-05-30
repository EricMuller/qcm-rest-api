package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.tag.TagId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface QuestionQuery {
    Page <QuestionWithTagsOnly> getQuestions(TagId[] tagIds,
                                             QuestionnaireId[] questionnaireIds,
                                             Pageable pageable, PrincipalId principal);

    Page <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal);

    Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal);

}
