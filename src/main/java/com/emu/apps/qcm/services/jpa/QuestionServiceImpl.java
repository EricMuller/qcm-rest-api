package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.projections.QuestionResponseProjection;
import com.emu.apps.qcm.services.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.services.jpa.repositories.QuestionTagRepository;
import com.emu.apps.qcm.services.jpa.repositories.QuestionnaireQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional()
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagCrudRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionTagRepository questionTagCrudRepository, QuestionnaireQuestionRepository questionnaireQuestionRepository) {
        this.questionRepository = questionRepository;
        this.questionTagCrudRepository = questionTagCrudRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional <Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionTag saveQuestionTag(QuestionTag questionTag) {
        return questionTagCrudRepository.save(questionTag);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Question> findAllByPage(Specification <Question> specification, Pageable pageable) {
        return questionRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Page <Question> findAllQuestionsTags(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page <QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable) {
        return questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaireId, pageable);
    }

    @Transactional(readOnly = true)
    public Iterable <QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId) {
        return questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaireId);
    }
}
