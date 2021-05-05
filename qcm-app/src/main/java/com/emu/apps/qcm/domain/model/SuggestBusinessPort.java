package com.emu.apps.qcm.domain.model;

import com.emu.apps.qcm.domain.model.questionnaire.Suggest;

public interface SuggestBusinessPort {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <Suggest> getSuggestions(String queryText);
}
