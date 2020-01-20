package com.emu.apps.qcm.services;


import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.projections.QuestionResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface QuestionService {

    Optional<Question> findById(Long id);

    void deleteById(Long id);

    Question saveQuestion(Question question);

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    Page<Question> findAllByPage(Specification<Question> specification, Pageable pageable);

    Page<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable);
}
