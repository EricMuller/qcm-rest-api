package com.emu.apps.qcm.domain.model;

import com.emu.apps.qcm.domain.model.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.model.published.PushishedQuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishedRepository {

    Page <PublishedQuestionnaireDto> getPublishedQuestionnaires(Pageable pageable);

    Iterable <String> getPublishedCategories();

    Iterable <String> getPublishedTags();

    Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionsByQuestionnaireUuid(String uuid);

    PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(String uuid);
}
