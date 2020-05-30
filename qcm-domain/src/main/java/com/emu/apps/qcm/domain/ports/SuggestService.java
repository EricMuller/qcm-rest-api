package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.web.dtos.SuggestDto;

public interface SuggestService {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <SuggestDto> getSuggestions(String queryText);
}
