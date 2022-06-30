package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionQuery;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.shared.application.ApplicationService;
import com.emu.apps.shared.events.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static com.emu.apps.qcm.application.events.AppEvents.QUESTION_CREATED;
import static java.util.Map.of;

@Service
public class QuestionCatalog implements ApplicationService {

    private final QuestionRepository questionRepository;

    private final QuestionQuery questionQuery;
    private final QuestionnaireIdMapper questionnaireIdMapper;

    private final TagIdMapper tagIdMapper;

    private final EventBus eventBus;

    public QuestionCatalog(QuestionRepository questionRepository, QuestionQuery questionQuery, QuestionnaireIdMapper questionnaireIdMapper, TagIdMapper tagIdMapper, @Qualifier("SimpleEventBus") EventBus eventBus) {
        this.questionRepository = questionRepository;
        this.questionQuery = questionQuery;
        this.questionnaireIdMapper = questionnaireIdMapper;
        this.tagIdMapper = tagIdMapper;
        this.eventBus = eventBus;
    }


    public Page <QuestionWithTagsOnly> getQuestions(String[] tagUuid,
                                                    String[] questionnaireUuid,
                                                    Pageable pageable, PrincipalId principal) {


        return questionQuery.getQuestions(tagIdMapper.toTagId(tagUuid), questionnaireIdMapper.toQuestionnaireId(questionnaireUuid), pageable, principal);
    }

    public Optional <Question> getQuestionById(QuestionId questionId) {

        return questionRepository.getQuestionOfId(questionId);
    }

    public Question updateQuestion(Question question, PrincipalId principal) {

        return questionRepository.updateQuestion(question, principal);
    }

    public Collection <Question> saveQuestions(Collection <Question> questions, PrincipalId principal) {

        return questionRepository.saveQuestions(questions, principal);
    }

    public Question saveQuestion(Question question, PrincipalId principal) {

        var createdQuestion = questionRepository.saveQuestion(question, principal);

        this.publishEvent(createEvent(QUESTION_CREATED, of("question_id", createdQuestion.getId().toUuid())));

        return createdQuestion;

    }

    public void deleteQuestionById(QuestionId questionId) {

        questionRepository.deleteQuestionOfId(questionId);
    }

    public Page <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal) {

        return questionQuery.findAllQuestionTagByPage(pageable, principal);
    }

    public Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal) {

        return questionQuery.findAllStatusByPage(pageable, principal);
    }

    @Override
    public EventBus getEventBus() {
        return null;
    }

    public Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException {

        return questionRepository.getTags(search, pageable, principal);
    }

}
