package com.emu.apps.qcm.repositories.adapters;

import com.emu.apps.qcm.repositories.PublishedRepository;
import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.spi.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.spi.persistence.mappers.PublishedMapper;
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
public class PublishedRepositoryAdapter implements PublishedRepository {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final QuestionPersistencePort questionPersistencePort;

    private final PublishedMapper publishedMapper;

    public PublishedRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort, QuestionPersistencePort questionPersistencePort, PublishedMapper publishedMapper) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.questionPersistencePort = questionPersistencePort;
        this.publishedMapper = publishedMapper;
    }
    /**
     * find  a list of questionnaires
     *
     *
     * @param pageable : paging
     * @return a list of questionnaires with the specified tag
     */
    @Transactional(readOnly = true)
    public Page <PublishedQuestionnaireDto> getPublishedQuestionnaires( Pageable pageable) {
        return questionnairePersistencePort.findAllPublishedByPage( pageable);
    }

    @Transactional(readOnly = true)
    public PublishedQuestionnaireDto getPublishedQuestionnaireByUuid(String uuid){
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

    @Override
    public Iterable <PushishedQuestionnaireQuestionDto> getPublishedQuestionsByQuestionnaireUuid(String uuid) {

        Iterable <QuestionnaireQuestionEntity>  questionnaireQuestions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);

        return publishedMapper.questionnaireQuestionsToPublishedDtos(questionnaireQuestions);

    }



}
