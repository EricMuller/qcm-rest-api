package com.emu.apps.qcm.domain;


import com.emu.apps.qcm.domain.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.entity.questions.Question;
import com.emu.apps.qcm.domain.entity.tags.QuestionTag;
import com.emu.apps.qcm.domain.jpa.projections.QuestionResponseProjection;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface QuestionDOService {

    Optional <Question> findById(Long id);

    void deleteById(Long id);

    Question saveQuestion(Question question);

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Page <Question> findAllByPage(Specification <Question> specification, Pageable pageable);

    Page <QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable);

    Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireId(Long questionnaireId);

}
