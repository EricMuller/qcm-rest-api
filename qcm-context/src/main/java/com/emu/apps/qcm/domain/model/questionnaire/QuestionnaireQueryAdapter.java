package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Questionnaire Business Delegate
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
class QuestionnaireQueryAdapter implements QuestionnaireQuery {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    public QuestionnaireQueryAdapter(QuestionnairePersistencePort questionnairePersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal) {
        return questionnairePersistencePort.findAllByPage(tagUuid, principal.toUuid(), pageable);
    }


}
