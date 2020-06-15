package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_QUESTIONNAIRES;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_QUESTIONNAIRES, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicQuestionnaireRestController {

    private final QuestionnaireServicePort questionnaireServicePort;

    public PublicQuestionnaireRestController(QuestionnaireServicePort questionnaireServicePort) {
        this.questionnaireServicePort = questionnaireServicePort;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    public Page <QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                     Pageable pageable,@RequestParam(value = "principal", required = false) String principal) {
        return questionnaireServicePort.getPublicQuestionnaires(tagUuid, pageable, principal);
    }



}
