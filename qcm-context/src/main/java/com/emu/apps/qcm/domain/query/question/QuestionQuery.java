package com.emu.apps.qcm.domain.query.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionQuery {
    Page <QuestionWithTagsOnly> findQuestionsOfTagIdAndQuestionnaireId(TagId[] tagIds,
                                                                       QuestionnaireId[] questionnaireIds,
                                                                       Pageable pageable, PrincipalId principal);

    Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal);

}
