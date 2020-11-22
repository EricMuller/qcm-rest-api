package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.aggregates.Question;
import com.emu.apps.qcm.aggregates.question.QuestionTags;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface QuestionRepository {
    Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                         String[] questionnaireUuid,
                                         Pageable pageable, String principal);

    Question getQuestionByUuId(String id);

    Question updateQuestion(Question questionDto, String principal);


    Collection <Question> saveQuestions(Collection <Question> questionDtos, String principal);


    Question saveQuestion(Question questionDto, String principal);

    void deleteQuestionByUuid(String uuid);
}
