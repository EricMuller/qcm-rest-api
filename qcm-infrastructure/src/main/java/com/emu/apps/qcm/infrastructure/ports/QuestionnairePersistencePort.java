package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import com.emu.apps.qcm.guest.GuestCategoryDto;
import com.emu.apps.qcm.guest.GuestTagDto;
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

    Iterable <GuestCategoryDto> getPublicCategories();

    Iterable <GuestTagDto> getPublicTags();
}
