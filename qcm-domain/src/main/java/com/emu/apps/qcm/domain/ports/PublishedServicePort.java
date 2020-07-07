package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.dtos.published.PushishedQuestionnaireQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishedServicePort {

    Page <PublishedQuestionnaireDto> getPublishedQuestionnaires(Pageable pageable);

    Iterable <String> getPublishedCategories();

    Iterable <String> getPublishedTags();

    Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionsByQuestionnaireUuid(String uuid);

}
