package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.web.dtos.SuggestDto;
import com.emu.apps.qcm.business.SuggestDelegate;
import com.emu.apps.qcm.webmvc.rest.SuggestRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class SuggestRestController implements SuggestRestApi {

    private SuggestDelegate suggestDelegate;

    public SuggestRestController(SuggestDelegate suggestDelegate) {
        this.suggestDelegate = suggestDelegate;
    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {

        return suggestDelegate.getSuggestions(queryText);
    }

}
