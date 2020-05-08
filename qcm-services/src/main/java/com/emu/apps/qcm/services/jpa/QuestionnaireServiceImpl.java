package com.emu.apps.qcm.services.jpa;


import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.services.jpa.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.services.jpa.repositories.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository, QuestionnaireQuestionRepository questionnaireQuestionRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
    }

    @Override
    @Transactional()
    public void deleteById(long id) {
        questionnaireRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Questionnaire findOne(long id) {
        return questionnaireRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Questionnaire> findAll() {
        return questionnaireRepository.findAll();
    }

    @Override
    public Questionnaire saveQuestionnaire(final Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    @Override
    public QuestionnaireQuestion saveQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion) {
        return questionnaireQuestionRepository.save(questionnaireQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<QuestionnaireProjection> findByTitleContaining(String title) {
        return questionnaireRepository.findByTitleContaining(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Questionnaire> findAllByPage(Specification<Questionnaire> specification, Pageable pageable) {
        return questionnaireRepository.findAll(specification, pageable);
    }

}
