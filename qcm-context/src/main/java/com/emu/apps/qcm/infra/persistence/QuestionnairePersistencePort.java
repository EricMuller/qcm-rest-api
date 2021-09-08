package com.emu.apps.qcm.infra.persistence;


import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionnairePersistencePort {

    Optional <Questionnaire> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page <Questionnaire> findAllByPage(String[] tagUuids, String principal, Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaireDto, String principal);

    QuestionnaireQuestion addQuestion(String uuid, String questionUuid, Optional <Integer> position, String principal);

    Page <Questionnaire> findAllPublishedByPage(Pageable pageable);

    Questionnaire findOnePublishedByUuid(String uuid);

    Iterable <String> findPublishedCategories();

    Iterable <String> findPublishedTags();

    void deleteQuestion(String questionnaireUuid, String questionUuid);

    QuestionnaireQuestion getQuestion(String questionnaireUuid, String questionUuid);

    Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(String questionnaireUuid, Pageable pageable);

    Iterable <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(String questionnaireUuid);

}
