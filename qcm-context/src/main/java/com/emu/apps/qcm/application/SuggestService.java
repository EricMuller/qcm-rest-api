package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.questionnaire.Suggest;

public interface SuggestService {
    @SuppressWarnings("squid:CommentedOutCodeLine")
    Iterable <Suggest> getSuggestions(String queryText);
}
