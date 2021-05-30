package com.emu.apps.qcm.application.suggest;


import com.emu.apps.qcm.application.SuggestService;
import com.emu.apps.qcm.domain.model.questionnaire.Suggest;
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
public class SuggestAdapter implements SuggestService {
    private final QuestionnaireReaderPort questionnaireReaderPort;

    public SuggestAdapter(QuestionnaireReaderPort questionnaireReaderPort) {
        this.questionnaireReaderPort = questionnaireReaderPort;

    }

    @Override
    @SuppressWarnings("squid:CommentedOutCodeLine")
    public Iterable <Suggest> getSuggestions(String queryText) {
        return questionnaireReaderPort.findByTitleContaining(queryText);
    }

}
