package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.PublishedServicePort;
import com.emu.apps.qcm.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.*;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLISHED_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublishedRestController {

    private final PublishedServicePort questionnaireServicePort;

    public PublishedRestController(PublishedServicePort publishedServicePort) {
        this.questionnaireServicePort = publishedServicePort;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    public Page <PublishedQuestionnaireDto> getQuestionnaires(Pageable pageable) {
        return questionnaireServicePort.getPublishedQuestionnaires(pageable);
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}")
    @ResponseBody
    public PublishedQuestionnaireDto getQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getPublishedQuestionnaireByUuid(uuid);
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <String> getCategories() {
        return questionnaireServicePort.getPublishedCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <String> getTags() {
        return questionnaireServicePort.getPublishedTags();
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}/" + QUESTIONS)
    @ResponseBody
    public Iterable <PushishedQuestionnaireQuestionDto> getQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getPublishedQuestionsByQuestionnaireUuid(uuid);
    }


}
