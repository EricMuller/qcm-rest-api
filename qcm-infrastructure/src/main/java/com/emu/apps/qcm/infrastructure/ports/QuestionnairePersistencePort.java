package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.models.QuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionnairePersistencePort {

    QuestionnaireDto findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page <QuestionnaireDto> findAllByPage(String tagUuids[], String principal, Pageable pageable);

    QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, String principal);

    QuestionDto addQuestion(String uuid, QuestionDto questionDto, Optional <Integer> position);

    Iterable <QuestionnaireProjection> findByTitleContaining(String title);

    Page <PublishedQuestionnaireDto> findAllPublishedByPage( Pageable pageable);

    PublishedQuestionnaireDto findOnePublishedByUuid(String uuid) ;

    Iterable <String> findPublishedCategories();

    Iterable <String> findPublishedTags();

    void deleteQuestion(String questionnaireUuid, String questionUuid) ;

    Page <QuestionnaireQuestionDto> getQuestionsProjectionByQuestionnaireUuid(String questionnaireUuid, Pageable pageable) ;
}
