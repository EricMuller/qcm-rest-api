package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.application.webhooks.PublishedServices;
import com.emu.apps.qcm.rest.controllers.resources.published.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.controllers.resources.published.PushishedQuestionnaireQuestion;

import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@Profile("webmvc")
@Tag(name = "Published")
@RequestMapping(value = PUBLISHED_API, produces = APPLICATION_JSON_VALUE)
public class PublishedRestController {

    private final PublishedServices publishedServices;

    public PublishedRestController(PublishedServices publishedServices) {
        this.publishedServices = publishedServices;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <PublishedQuestionnaire> getPublishedQuestionnaires(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {
        return publishedServices.getPublishedQuestionnaires(pageable);
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}")
    @ResponseBody
    public PublishedQuestionnaire getPublishedQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return publishedServices.getPublishedQuestionnaireByUuid(uuid);
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <String> getPublishedCategories() {
        return publishedServices.getPublishedCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <String> getPublishedTags() {
        return publishedServices.getPublishedTags();
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}/" + QUESTIONS)
    @ResponseBody
    public Iterable <PushishedQuestionnaireQuestion> getPublishedQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return publishedServices.getPublishedQuestionsByQuestionnaireUuid(uuid);
    }


}
