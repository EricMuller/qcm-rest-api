package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.question.Question;
import com.emu.apps.qcm.domain.models.question.QuestionId;
import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Questionnaire Business Delegate
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class QuestionnaireRepositoryAdapter implements QuestionnaireRepository {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    public QuestionnaireRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
    }

    /**
     * Find a Questionnaire with technical identifier
     *
     * @param questionnaireUuid questionnaire uuid
     * @return the questionnaire
     */
    @Override
    @Transactional(readOnly = true)
    public Optional <Questionnaire> getQuestionnaireById(QuestionnaireId questionnaireUuid) {
        return questionnairePersistencePort.findByUuid(questionnaireUuid.toUUID());

    }

    /**
     * Delete a Questionnaire with technical identifier
     *
     * @param questionnaireId questionnaire Uuid
     * @return Nocontent
     */
    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(QuestionnaireId questionnaireId) {
        questionnairePersistencePort.deleteByUuid(questionnaireId.toUUID());
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update a existing questionnaire
     *
     * @param questionnaire the questionnaire DTO
     * @return the updated questionnaire
     */
    @Override
    public Questionnaire updateQuestionnaire(Questionnaire questionnaire, PrincipalId principal) {
        return questionnairePersistencePort.saveQuestionnaire(questionnaire, principal.toUUID());
    }

    /**
     * Create a  new Questionnaire
     *
     * @param questionnaire the questionnaire DTO
     * @return the created questionnaire
     */
    @Override
    public Questionnaire saveQuestionnaire(Questionnaire questionnaire, PrincipalId principal) {
        return questionnairePersistencePort.saveQuestionnaire(questionnaire, principal.toUUID());
    }

    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireId(QuestionnaireId questionnaireId, Pageable pageable) {
        return questionnairePersistencePort.getQuestionsByQuestionnaireUuid(questionnaireId.toUUID(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal) {
        return questionnairePersistencePort.findAllByPage(tagUuid, principal.toUUID(), pageable);
    }

    /**
     * Add  question to a questionnaire
     *
     * @param uuid     questionnaire UUID
     * @param question the question DTO
     * @return QuestionDto
     */
    @Override
    public Question addQuestion(QuestionnaireId questionnaireId, Question question, Optional <Integer> position, PrincipalId principal) {

        return questionnairePersistencePort.addQuestion(questionnaireId.toUUID(), question, position, principal.toUUID());
    }

    /**
     * Add  questions to a questionnaire
     *
     * @param questionnaireId UUI Questionnaire
     * @param questionDtos    List of questions to add
     */
    @Override
    public List <Question> addQuestions(QuestionnaireId questionnaireId, Collection <Question> questionDtos, PrincipalId principal) {

        AtomicInteger position = new AtomicInteger(0);
        return questionDtos
                .stream()
                .map(questionDto -> addQuestion(questionnaireId, questionDto, Optional.of(position.incrementAndGet()), principal))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        questionnairePersistencePort.deleteQuestion(questionnaireId.toUUID(), questionId.toUUID());
    }

    @Override
    public QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        return questionnairePersistencePort.getQuestion(questionnaireId.toUUID(), questionId.toUUID());
    }

    @Override
    public void activateQuestionnaire(QuestionnaireId questionnaireId) {

    }

}
