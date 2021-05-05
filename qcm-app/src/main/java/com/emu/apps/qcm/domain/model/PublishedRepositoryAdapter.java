package com.emu.apps.qcm.domain.model;

import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.rest.controllers.mappers.PublishedMapper;
import com.emu.apps.qcm.rest.controllers.resources.published.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.controllers.resources.published.PushishedQuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Questionnaire Business Delegate
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class PublishedRepositoryAdapter implements PublishedRepository {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final QuestionPersistencePort questionPersistencePort;

    private final PublishedMapper publishedMapper;

    public PublishedRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort, QuestionPersistencePort questionPersistencePort,
                                      PublishedMapper publishedMapper) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.questionPersistencePort = questionPersistencePort;
        this.publishedMapper = publishedMapper;
    }

    /**
     * find  a list of questionnaires
     *
     * @param pageable : paging
     * @return a list of questionnaires with the specified tag
     */
    @Transactional(readOnly = true)
    public Page <PublishedQuestionnaire> getPublishedQuestionnaires(Pageable pageable) {
        return publishedMapper.pageQuestionnaireToPublishedQuestionnaires(questionnairePersistencePort.findAllPublishedByPage(pageable));
    }

    @Transactional(readOnly = true)
    public PublishedQuestionnaire getPublishedQuestionnaireByUuid(String uuid) {
        return publishedMapper.questionnaireToPublishedQuestionnaire(questionnairePersistencePort.findOnePublishedByUuid(uuid));
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedCategories() {
        return questionnairePersistencePort.findPublishedCategories();
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedTags() {
        return questionnairePersistencePort.findPublishedTags();
    }

    @Override
    public Iterable <PushishedQuestionnaireQuestion> getPublishedQuestionsByQuestionnaireUuid(String uuid) {
//
//        Iterable <QuestionnaireQuestionEntity>  questionnaireQuestions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);
//
//        return publishedMapper.questionnaireQuestionsToPublishedDtos(questionnaireQuestions);

        return new ArrayList <>();

    }


}
