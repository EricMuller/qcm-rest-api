package com.emu.apps.qcm.webmvc.services;

import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.webmvc.services.jpa.projections.QuestionnaireProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface QuestionnaireService {

    Questionnaire findOne(long id);

    void deleteById(long id);

    Iterable<Questionnaire> findAll();

    Page<Questionnaire> findAllByPage(Specification<Questionnaire> specification, Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire);

    QuestionnaireQuestion saveQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion);

    Iterable<QuestionnaireProjection> findByTitleContaining(String title);

}
