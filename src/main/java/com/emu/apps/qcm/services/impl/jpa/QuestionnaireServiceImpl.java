package com.emu.apps.qcm.services.impl.jpa;


import com.emu.apps.qcm.services.QuestionnaireService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.projections.QuestionnaireProjection;
import com.emu.apps.qcm.services.repositories.QuestionnaireQuestionRepository;
import com.emu.apps.qcm.services.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.services.repositories.QuestionnaireTagRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
public class QuestionnaireServiceImpl implements QuestionnaireService {

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private QuestionnaireTagRepository questionnaireTagRepository;

    @Override
    public void deleteById(long id) {
        questionnaireQuestionRepository.deleteByQuestionnaireId(id);
        questionnaireTagRepository.deleteByQuestionnaireId(id);
        questionnaireRepository.delete(id);
    }

    @Override
    public Questionnaire findById(long id) {
        return questionnaireRepository.findOne(id);
    }

    @Override
    public Iterable<Questionnaire> findAll() {
        return questionnaireRepository.findAll();
    }

    @Override
    public Page<Questionnaire> findAllByPage(Pageable pageable) {
        return questionnaireRepository.findAll(pageable);
    }

    @Override
    public Questionnaire saveQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    @Override
    public Iterable<Questionnaire> saveQuestionnaire(Iterable<Questionnaire> questionnaires) {
        return questionnaireRepository.save(questionnaires);
    }

    @Override
    public QuestionnaireQuestion saveQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion) {
        return questionnaireQuestionRepository.save(questionnaireQuestion);
    }

    @Override
    public Iterable<QuestionnaireProjection> findByTitleContaining(String title) {
        return questionnaireRepository.findByTitleContaining(title);
    }


}
