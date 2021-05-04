package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.PublishedRepository;
import com.emu.apps.qcm.rest.controllers.resources.published.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.controllers.resources.published.PushishedQuestionnaireQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class PublishedServices {

    private final PublishedRepository publishedRepository;

    public PublishedServices(PublishedRepository publishedRepository) {
        this.publishedRepository = publishedRepository;
    }

    /**
     * find  a list of questionnaires
     *
     * @param pageable : paging
     * @return a list of questionnaires with the specified tag
     */
    @Transactional(readOnly = true)
    public Page <PublishedQuestionnaire> getPublishedQuestionnaires(Pageable pageable) {
        return publishedRepository.getPublishedQuestionnaires(pageable);
    }

    @Transactional(readOnly = true)
    public PublishedQuestionnaire getPublishedQuestionnaireByUuid(String uuid) {
        return publishedRepository.getPublishedQuestionnaireByUuid(uuid);
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedCategories() {
        return publishedRepository.getPublishedCategories();
    }

    @Transactional(readOnly = true)
    public Iterable <String> getPublishedTags() {
        return publishedRepository.getPublishedTags();
    }

    public Iterable <PushishedQuestionnaireQuestion> getPublishedQuestionsByQuestionnaireUuid(String uuid) {
//
//        Iterable <QuestionnaireQuestionEntity>  questionnaireQuestions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);
//
//        return publishedMapper.questionnaireQuestionsToPublishedDtos(questionnaireQuestions);

        return new ArrayList <>();

    }

}
