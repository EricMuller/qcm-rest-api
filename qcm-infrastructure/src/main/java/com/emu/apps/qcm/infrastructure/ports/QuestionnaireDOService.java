package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface QuestionnaireDOService {

    Questionnaire findOne(long id);

    Questionnaire findOneAndQuestions(long id) ;

    void deleteById(long id);

    Iterable<Questionnaire> findAll();

    Page<Questionnaire> findAllByPage(Specification <Questionnaire> specification, Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire);

    QuestionnaireQuestion saveQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion);

    Iterable<QuestionnaireProjection> findByTitleContaining(String title);

}
