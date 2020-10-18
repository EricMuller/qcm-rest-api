package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.dtos.SuggestDto;

public interface SuggestBusinessPort {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <SuggestDto> getSuggestions(String queryText);
}
