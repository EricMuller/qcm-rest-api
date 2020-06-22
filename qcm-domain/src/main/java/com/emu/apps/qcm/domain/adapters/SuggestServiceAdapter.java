package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.domain.ports.SuggestServicePort;
import com.emu.apps.qcm.infrastructure.ports.QuestionnairePersistencePort;
import com.emu.apps.qcm.dtos.SuggestDto;
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
public class SuggestServiceAdapter implements SuggestServicePort {
    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final SuggestMapper suggestMapper;

    public SuggestServiceAdapter(QuestionnairePersistencePort questionnairePersistencePort, SuggestMapper suggestMapper) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.suggestMapper = suggestMapper;
    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <SuggestDto> getSuggestions(String queryText) {
        final List <SuggestDto> suggestions = new ArrayList <>();
        if (StringUtils.isNoneEmpty(queryText)) {
            suggestMapper.modelsToSuggestDtos(questionnairePersistencePort.findByTitleContaining(queryText)).forEach(suggestions::add);
            // tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

}
