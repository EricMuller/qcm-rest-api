package com.emu.apps.qcm.domain.repositories.adapters;


import com.emu.apps.qcm.domain.repositories.SuggestBusinessPort;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.domain.dtos.SuggestDto;
import com.emu.apps.qcm.infra.persistence.mappers.SuggestMapper;
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
public class SuggestRepositoryAdapter implements SuggestBusinessPort {
    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final SuggestMapper suggestMapper;

    public SuggestRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort, SuggestMapper suggestMapper) {
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
