package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.webmvc.services.QuestionnaireService;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import com.emu.apps.qcm.web.mappers.SuggestMapper;
import com.emu.apps.qcm.webmvc.rest.SuggestRestApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class SuggestRestController implements SuggestRestApi {
    private final QuestionnaireService questionnairesService;

    private final SuggestMapper suggestMapper;


    @Autowired
    public SuggestRestController(QuestionnaireService questionnairesService, SuggestMapper suggestMapper) {
        this.questionnairesService = questionnairesService;
        this.suggestMapper = suggestMapper;
    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {
        final List<SuggestDto> suggestions = new ArrayList <>();
        if (StringUtils.isNoneEmpty(queryText)) {
            suggestMapper.modelsToSuggestDtos(questionnairesService.findByTitleContaining(queryText)).forEach(suggestions::add);
            // tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

}
