package com.emu.apps.qcm.application.webhooks;

import com.emu.apps.qcm.rest.controllers.resources.published.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.controllers.resources.published.PushishedQuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishedRepository {

    Page <PublishedQuestionnaire> getPublishedQuestionnaires(Pageable pageable);

    Iterable <String> getPublishedCategories();

    Iterable <String> getPublishedTags();

    Iterable <PushishedQuestionnaireQuestion> getPublishedQuestionsByQuestionnaireUuid(String uuid);

    PublishedQuestionnaire getPublishedQuestionnaireByUuid(String uuid);
}
