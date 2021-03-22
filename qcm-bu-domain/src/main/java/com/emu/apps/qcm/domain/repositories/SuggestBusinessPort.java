package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.questionnaire.Suggest;

public interface SuggestBusinessPort {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <Suggest> getSuggestions(String queryText);
}
