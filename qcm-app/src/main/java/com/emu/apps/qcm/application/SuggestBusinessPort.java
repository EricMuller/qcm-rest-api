package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.questionnaire.Suggest;

public interface SuggestBusinessPort {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <Suggest> getSuggestions(String queryText);
}
