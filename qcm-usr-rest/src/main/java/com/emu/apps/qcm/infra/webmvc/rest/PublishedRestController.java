package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.qcm.domain.repositories.PublishedRepository;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.*;
import static org.springframework.data.domain.Sort.Direction.DESC;


@RestController
@Profile("webmvc")
@Tag(name = "Published")
@RequestMapping(value = PUBLISHED_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublishedRestController {

    private final PublishedRepository publishedRepository;

    public PublishedRestController(PublishedRepository publishedServicePort) {
        this.publishedRepository = publishedServicePort;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <PublishedQuestionnaireDto> getPublishedQuestionnaires(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {
        return publishedRepository.getPublishedQuestionnaires(pageable);
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}")
    @ResponseBody
    public PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return publishedRepository.getPublishedQuestionnaireByUuid(uuid);
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <String> getPublishedCategories() {
        return publishedRepository.getPublishedCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <String> getPublishedTags() {
        return publishedRepository.getPublishedTags();
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}/" + QUESTIONS)
    @ResponseBody
    public Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return publishedRepository.getPublishedQuestionsByQuestionnaireUuid(uuid);
    }


}
