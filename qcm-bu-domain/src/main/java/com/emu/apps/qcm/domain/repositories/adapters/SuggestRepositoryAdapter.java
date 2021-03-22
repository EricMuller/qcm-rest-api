package com.emu.apps.qcm.domain.repositories.adapters;


import com.emu.apps.qcm.domain.models.questionnaire.Suggest;
import com.emu.apps.qcm.domain.repositories.SuggestBusinessPort;
import com.emu.apps.qcm.infra.persistence.QuestionnaireReaderPort;
import org.springframework.stereotype.Service;

/**
 * Suggest Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */

@Service
public class SuggestRepositoryAdapter implements SuggestBusinessPort {
    private final QuestionnaireReaderPort questionnaireReaderPort;

    public SuggestRepositoryAdapter(QuestionnaireReaderPort questionnaireReaderPort) {
        this.questionnaireReaderPort = questionnaireReaderPort;

    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <Suggest> getSuggestions(String queryText) {
        return questionnaireReaderPort.findByTitleContaining(queryText);
    }

}
