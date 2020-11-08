package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.api.dtos.published.PushishedQuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishedBusinessPort {

    Page <PublishedQuestionnaireDto> getPublishedQuestionnaires(Pageable pageable);

    Iterable <String> getPublishedCategories();

    Iterable <String> getPublishedTags();

    Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionsByQuestionnaireUuid(String uuid);

    PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(String uuid);
}