package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.dtos.published.PushishedQuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishedRepository {

    Page <PublishedQuestionnaireDto> getPublishedQuestionnaires(Pageable pageable);

    Iterable <String> getPublishedCategories();

    Iterable <String> getPublishedTags();

    Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionsByQuestionnaireUuid(String uuid);

    PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(String uuid);
}
