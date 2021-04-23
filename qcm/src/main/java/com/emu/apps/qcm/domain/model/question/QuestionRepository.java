package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface QuestionRepository {
    Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                         String[] questionnaireUuid,
                                         Pageable pageable, PrincipalId principal);

    Optional <Question> getQuestionById(QuestionId questionId);

    Question updateQuestion(Question question, PrincipalId principal);

    Collection <Question> saveQuestions(Collection <Question> questions, PrincipalId principal);

    Question saveQuestion(Question question, @NotNull PrincipalId principal);

    void deleteQuestionById(QuestionId questionId);

    Iterable <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal);

    Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal);

}
