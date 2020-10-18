package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.question.QuestionTags;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface QuestionBusinessPort {
    Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                         String[] questionnaireUuid,
                                         Pageable pageable, String principal);

    Question getQuestionByUuId(String id);

    Question updateQuestion(Question questionDto, String principal);


    Collection <Question> saveQuestions(Collection <Question> questionDtos, String principal);


    Question saveQuestion(Question questionDto, String principal);

    void deleteQuestionByUuid(String uuid);
}
