package com.emu.apps.qcm.business;


import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import com.emu.apps.qcm.web.mappers.SuggestMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by eric on 05/06/2017.
 */
@Service
public class SuggestDelegate {
    private final QuestionnaireService questionnairesService;

    private final SuggestMapper suggestMapper;

    @Autowired
    public SuggestDelegate(QuestionnaireService questionnairesService, SuggestMapper suggestMapper) {
        this.questionnairesService = questionnairesService;
        this.suggestMapper = suggestMapper;
    }


    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(String queryText) {
        final List <SuggestDto> suggestions = new ArrayList <>();
        if (StringUtils.isNoneEmpty(queryText)) {
            suggestMapper.modelsToSuggestDtos(questionnairesService.findByTitleContaining(queryText)).forEach(suggestions::add);
            // tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

}
