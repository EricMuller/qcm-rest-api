package com.emu.apps.qcm.rest.controllers.domain;



import com.emu.apps.qcm.application.suggest.SuggestService;
import com.emu.apps.qcm.domain.model.questionnaire.Suggest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.DOMAIN_API;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.SUGGEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping(value = DOMAIN_API + SUGGEST, produces = APPLICATION_JSON_VALUE)

public class SuggestRestController {

    private final SuggestService suggestService;

    public SuggestRestController(SuggestService suggestServicePort) {
        this.suggestService = suggestServicePort;
    }

    @GetMapping(value = "/title")
    @ResponseBody
    @Operation(hidden = true)
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <Suggest> getSuggestions(@RequestParam("queryText") String queryText) {

        return suggestService.getSuggestions(queryText);
    }

}
