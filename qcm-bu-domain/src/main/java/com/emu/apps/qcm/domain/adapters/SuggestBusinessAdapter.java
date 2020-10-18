package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.domain.ports.SuggestBusinessPort;
import com.emu.apps.qcm.spi.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.api.dtos.SuggestDto;
import com.emu.apps.qcm.spi.persistence.mappers.SuggestMapper;
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
public class SuggestBusinessAdapter implements SuggestBusinessPort {
    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final SuggestMapper suggestMapper;

    public SuggestBusinessAdapter(QuestionnairePersistencePort questionnairePersistencePort, SuggestMapper suggestMapper) {
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
