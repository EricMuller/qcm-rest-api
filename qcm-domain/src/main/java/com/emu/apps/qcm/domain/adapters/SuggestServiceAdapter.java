package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.domain.ports.SuggestService;
import com.emu.apps.qcm.infrastructure.ports.QuestionnaireDOService;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import com.emu.apps.qcm.mappers.SuggestMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Suggest Business Delegate
 *<p>
 *
 * @since 2.2.0
 * @author eric
 */

@Service
public class SuggestServiceAdapter implements SuggestService {
    private final QuestionnaireDOService questionnaireDOService;

    private final SuggestMapper suggestMapper;

    public SuggestServiceAdapter(QuestionnaireDOService questionnaireDOService, SuggestMapper suggestMapper) {
        this.questionnaireDOService = questionnaireDOService;
        this.suggestMapper = suggestMapper;
    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(String queryText) {
        final List <SuggestDto> suggestions = new ArrayList <>();
        if (StringUtils.isNoneEmpty(queryText)) {
            suggestMapper.modelsToSuggestDtos(questionnaireDOService.findByTitleContaining(queryText)).forEach(suggestions::add);
            // tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

}
