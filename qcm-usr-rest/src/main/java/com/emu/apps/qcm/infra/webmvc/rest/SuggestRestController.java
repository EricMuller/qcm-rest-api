package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.repositories.SuggestBusinessPort;
import com.emu.apps.qcm.domain.models.questionnaire.Suggest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.SUGGEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + SUGGEST, produces = APPLICATION_JSON_VALUE)

public class SuggestRestController {

    private final SuggestBusinessPort suggestBusinessPort;

    public SuggestRestController(SuggestBusinessPort suggestServicePort) {
        this.suggestBusinessPort = suggestServicePort;
    }

    @GetMapping(value = "/title")
    @ResponseBody
    @Operation(hidden = true)
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <Suggest> getSuggestions(@RequestParam("queryText") String queryText) {

        return suggestBusinessPort.getSuggestions(queryText);
    }

}
