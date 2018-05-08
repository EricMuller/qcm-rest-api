package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.projections.QuestionnaireProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireService {

    Questionnaire findById(long id);

    void deleteById(long id) ;

    Iterable<Questionnaire> findAll();

    Page<Questionnaire> findAllByPage(Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire);

    Iterable<Questionnaire> saveQuestionnaire(Iterable<Questionnaire> questionnaires);

    QuestionnaireQuestion saveQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion);

    Iterable<QuestionnaireProjection> findByTitleContaining(String title);


}
