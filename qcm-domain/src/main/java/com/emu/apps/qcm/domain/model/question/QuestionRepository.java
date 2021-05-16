package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface QuestionRepository {
    Page <QuestionTags> getQuestions(String[] tagUuid,
                                     String[] questionnaireUuid,
                                     Pageable pageable, PrincipalId principal);

    Optional <Question> getQuestionById(QuestionId questionId);

    Question updateQuestion(Question question, PrincipalId principal);

    Collection <Question> saveQuestions(Collection <Question> questions, PrincipalId principal);

    Question saveQuestion(Question question, PrincipalId principal);

    void deleteQuestionById(QuestionId questionId);

    Iterable <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal);

    Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal);

}
