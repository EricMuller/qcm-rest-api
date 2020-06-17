package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.guest.GuestCategoryDto;
import com.emu.apps.qcm.guest.GuestTagDto;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.GUEST_API;


@RestController
@Profile("webmvc")
@RequestMapping(value = GUEST_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class GuestRestController {

    private final QuestionnaireServicePort questionnaireServicePort;

    public GuestRestController(QuestionnaireServicePort questionnaireServicePort) {
        this.questionnaireServicePort = questionnaireServicePort;
    }

    @GetMapping(value = "/questionnaires")
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                     Pageable pageable, @RequestParam(value = "principal", required = false) String principal) {
        return questionnaireServicePort.getPublicQuestionnaires(tagUuid, pageable, principal);
    }

    @GetMapping(value = "/categories")
    @ResponseBody
    public Iterable <GuestCategoryDto> getCategories() {
        return questionnaireServicePort.getPublicCategories();
    }

    @GetMapping(value = "/tags")
    @ResponseBody
    public Iterable <GuestTagDto> getTags() {
        return questionnaireServicePort.getPublicTags();
    }

}
