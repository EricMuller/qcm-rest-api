package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.dtos.published.PublishedCategoryDto;
import com.emu.apps.qcm.dtos.published.PublishedTagDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionnairePersistencePort {

    QuestionnaireDto findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page <QuestionnaireDto> findAllByPage(String tagUuids[], String principal, Pageable pageable);

    Page <QuestionnaireDto> findAllPublicByPage(String tagUuids[], String principal, Pageable pageable);

    QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, String principal);

    QuestionDto addQuestion(String uuid, QuestionDto questionDto, Optional <Long> position);

    Iterable <QuestionnaireProjection> findByTitleContaining(String title);

    Iterable <PublishedCategoryDto> getPublicCategories();

    Iterable <PublishedTagDto> getPublicTags();

    void deleteQuestion(String questionnaireUuid, String questionUuid) ;
}
