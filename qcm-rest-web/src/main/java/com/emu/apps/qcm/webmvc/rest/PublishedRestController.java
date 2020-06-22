package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.dtos.published.PublishedCategoryDto;
import com.emu.apps.qcm.dtos.published.PublishedTagDto;
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

    private final QuestionnaireServicePort questionnaireServicePort;

    public PublishedRestController(QuestionnaireServicePort questionnaireServicePort) {
        this.questionnaireServicePort = questionnaireServicePort;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                     Pageable pageable, @RequestParam(value = "principal", required = false) String principal) {
        return questionnaireServicePort.getPublicQuestionnaires(tagUuid, pageable, principal);
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <PublishedCategoryDto> getCategories() {
        return questionnaireServicePort.getPublicCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <PublishedTagDto> getTags() {
        return questionnaireServicePort.getPublicTags();
    }

}
