package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.model.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionnairePersistencePort {

    Optional<Questionnaire> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page <Questionnaire> findAllByPage(String[] tagUuids, String principal, Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaireDto, String principal);

    Question addQuestion(String uuid, Question questionDto, Optional <Integer> position, String principal);

//    Iterable <QuestionnaireProjection> findByTitleContaining(String title);

    Page <PublishedQuestionnaireDto> findAllPublishedByPage(Pageable pageable);

    PublishedQuestionnaireDto findOnePublishedByUuid(String uuid);

    Iterable <String> findPublishedCategories();

    Iterable <String> findPublishedTags();

    void deleteQuestion(String questionnaireUuid, String questionUuid);

    QuestionnaireQuestion getQuestion(String questionnaireUuid, String questionUuid);

    Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(String questionnaireUuid, Pageable pageable);


}
