package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.domain.ports.SuggestBusinessPort;
import com.emu.apps.qcm.api.dtos.SuggestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.SUGGEST;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + SUGGEST, produces = MediaType.APPLICATION_JSON_VALUE)

public class SuggestRestController {

    private final SuggestBusinessPort suggestBusinessPort;

    public SuggestRestController(SuggestBusinessPort suggestServicePort) {
        this.suggestBusinessPort = suggestServicePort;
    }

    @GetMapping(value = "/title")
    @ResponseBody
    @Operation(hidden = true)
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {

        return suggestBusinessPort.getSuggestions(queryText);
    }

}
