package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.domain.dtos.SuggestDto;

public interface SuggestBusinessPort {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <SuggestDto> getSuggestions(String queryText);
}
