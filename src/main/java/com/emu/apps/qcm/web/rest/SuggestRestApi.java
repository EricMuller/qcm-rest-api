package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.SuggestDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + "/suggest")
public interface SuggestRestApi {

    @GetMapping(value = "/title", produces = "application/json")
    @ResponseBody
    Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText);
}
