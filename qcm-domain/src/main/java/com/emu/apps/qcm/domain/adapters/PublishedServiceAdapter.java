package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.PublishedServicePort;
import com.emu.apps.qcm.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.ports.QuestionPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.QuestionnairePersistencePort;
import com.emu.apps.qcm.mappers.PublishedMapper;
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
public class PublishedServiceAdapter implements PublishedServicePort {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final QuestionPersistencePort questionPersistencePort;

    private final PublishedMapper publishedMapper;

    public PublishedServiceAdapter(QuestionnairePersistencePort questionnairePersistencePort, QuestionPersistencePort questionPersistencePort, PublishedMapper publishedMapper) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.questionPersistencePort = questionPersistencePort;
        this.publishedMapper = publishedMapper;
    }
    /**
     * find  a list of questionnaires
     *
     *
     * @param pageable
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

        Iterable <QuestionnaireQuestion>  questionnaireQuestions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);

        return publishedMapper.questionnaireQuestionsToPublishedDtos(questionnaireQuestions);

    }



}
