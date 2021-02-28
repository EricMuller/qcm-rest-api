package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Tag;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface QuestionRepository {
    Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                         String[] questionnaireUuid,
                                         Pageable pageable, String principal);

    Optional <Question> getQuestionByUuId(String id);

    Question updateQuestion(Question questionDto, String principal);


    Collection <Question> saveQuestions(Collection <Question> questionDtos, String principal);


    Question saveQuestion(Question questionDto, @NotNull String principal);

    void deleteQuestionByUuid(String uuid);

    Iterable <Tag> findAllQuestionTagByPage(Pageable pageable, String principal);
}
