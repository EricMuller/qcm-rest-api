package com.emu.apps.qcm.services;


import com.emu.apps.qcm.metrics.Timer;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.projections.QuestionResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface QuestionService {

    Question findOne(Long id);

    Question saveQuestion(Question question);

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    @Timer
    Page<Question> findAllByPage(Specification<Question> specification, Pageable pageable);

    @Timer
    Page<Question> findAllQuestionsTags(Pageable pageable) ;

    @Timer
    Page<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable);

    @Timer
    Iterable<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId);

}
