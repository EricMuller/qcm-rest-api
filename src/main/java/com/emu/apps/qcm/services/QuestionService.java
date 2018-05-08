package com.emu.apps.qcm.services;


import com.emu.apps.qcm.metrics.Timer;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.projections.QuestionResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    Question findOne(Long id);

    Question saveQuestion(Question question);

    QuestionTag saveQuestionTag(QuestionTag questionTag);

    @Timer
    Page<Question> findAllQuestionsTags(Pageable pageable) ;

    @Timer
    Page<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable);

    @Timer
    Iterable<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId);

}
