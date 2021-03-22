package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.models.questionnaire.Suggest;

public interface QuestionnaireReaderPort {

    Iterable <Suggest> findByTitleContaining(String queryText);

}
