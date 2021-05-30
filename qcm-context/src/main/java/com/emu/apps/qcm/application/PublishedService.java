package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PublishedService {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final QuestionPersistencePort questionPersistencePort;


    public PublishedService(QuestionnairePersistencePort questionnairePersistencePort, QuestionPersistencePort questionPersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.questionPersistencePort = questionPersistencePort;
    }

    /**
     * find  a list of questionnaires
     *
     * @param pageable : paging
     * @return a list of questionnaires with the specified tag
     */
    @Transactional(readOnly = true)
    public Page <Questionnaire> getPublishedQuestionnaires(Pageable pageable) {
        return questionnairePersistencePort.findAllPublishedByPage(pageable);
    }

    @Transactional(readOnly = true)
    public Questionnaire getPublishedQuestionnaireByUuid(String uuid) {
        return questionnairePersistencePort.findOnePublishedByUuid(uuid);
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedCategories() {
        return questionnairePersistencePort.findPublishedCategories();
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedTags() {
        return questionnairePersistencePort.findPublishedTags();
    }

    @Transactional(readOnly = true)
    public Iterable <QuestionnaireQuestion> getPublishedQuestionsByQuestionnaireUuid(String uuid) {
        return questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);

    }

}
