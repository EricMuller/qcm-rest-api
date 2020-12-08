package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Questionnaire;
import com.emu.apps.qcm.domain.models.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.projections.QuestionnaireProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionnairePersistencePort {

    Optional<Questionnaire> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page <Questionnaire> findAllByPage(String[] tagUuids, String principal, Pageable pageable);

    Questionnaire saveQuestionnaire(Questionnaire questionnaireDto, String principal);

    Question addQuestion(String uuid, Question questionDto, Optional <Integer> position, String principal);

    Iterable <QuestionnaireProjection> findByTitleContaining(String title);

    Page <PublishedQuestionnaireDto> findAllPublishedByPage(Pageable pageable);

    PublishedQuestionnaireDto findOnePublishedByUuid(String uuid);

    Iterable <String> findPublishedCategories();

    Iterable <String> findPublishedTags();

    void deleteQuestion(String questionnaireUuid, String questionUuid);

    Page <QuestionnaireQuestion> getQuestionsProjectionByQuestionnaireUuid(String questionnaireUuid, Pageable pageable);
}
