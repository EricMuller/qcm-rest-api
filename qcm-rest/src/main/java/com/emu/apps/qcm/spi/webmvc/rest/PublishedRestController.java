package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.domain.ports.PublishedServicePort;
import com.emu.apps.qcm.api.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.api.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.*;


@RestController
@Profile("webmvc")
@Tag(name = "Published")
@RequestMapping(value = PUBLISHED_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublishedRestController {

    private final PublishedServicePort questionnaireServicePort;

    public PublishedRestController(PublishedServicePort publishedServicePort) {
        this.questionnaireServicePort = publishedServicePort;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    public Page <PublishedQuestionnaireDto> getPublishedQuestionnaires( @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                        @RequestParam(value = "count", defaultValue = "100", required = false) int size,
                                                                        @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                                                                        @RequestParam(value = "sort", defaultValue = "dateModification", required = false) String sortProperty) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));

        return questionnaireServicePort.getPublishedQuestionnaires(pageable);
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}")
    @ResponseBody
    public PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getPublishedQuestionnaireByUuid(uuid);
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <String> getPublishedCategories() {
        return questionnaireServicePort.getPublishedCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <String> getPublishedTags() {
        return questionnaireServicePort.getPublishedTags();
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}/" + QUESTIONS)
    @ResponseBody
    public Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return questionnaireServicePort.getPublishedQuestionsByQuestionnaireUuid(uuid);
    }


}
