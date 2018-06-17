package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.metrics.Timer;
import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.projections.QuestionResponseProjection;
import com.emu.apps.qcm.services.repositories.QuestionRepository;
import com.emu.apps.qcm.services.repositories.QuestionTagRepository;
import com.emu.apps.qcm.services.repositories.QuestionnaireQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional()
public class QuestionServiceImpl implements QuestionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionTagRepository questionTagCrudRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Override
    @Transactional(readOnly = true)

    public Question findOne(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    //@CacheEvict("question")
    public void deleteById(Long id) {
        questionRepository.delete(id);
    }

    @Override
    //@CacheEvict("question")
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionTag saveQuestionTag(QuestionTag questionTag) {
        return questionTagCrudRepository.save(questionTag);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAllByPage(Specification<Question> specification, Pageable pageable) {
        return questionRepository.findAll(specification, pageable);
    }


    @Transactional(readOnly = true)
    public Page<Question> findAllQuestionsTags(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId, Pageable pageable) {
        return questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaireId, pageable);
    }

    @Transactional(readOnly = true)
    public Iterable<QuestionResponseProjection> getQuestionsProjectionByQuestionnaireId(Long questionnaireId) {
        return questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaireId);
    }
}
