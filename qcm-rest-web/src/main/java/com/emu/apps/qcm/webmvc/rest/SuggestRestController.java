package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.adapters.SuggestServiceAdapter;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.SUGGEST;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = SUGGEST, produces = MediaType.APPLICATION_JSON_VALUE)
public class SuggestRestController {

    private final SuggestServiceAdapter suggestService;

    public SuggestRestController(SuggestServiceAdapter suggestService) {
        this.suggestService = suggestService;
    }

    @GetMapping(value = "/title")
    @ResponseBody
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {

        return suggestService.getSuggestions(queryText);
    }

}
