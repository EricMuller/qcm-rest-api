package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Questionnaire;
import com.emu.apps.qcm.domain.models.QuestionnaireQuestion;
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
    public Optional<Questionnaire> getQuestionnaireByUuid(String questionnaireUuid) {
        return questionnairePersistencePort.findByUuid(questionnaireUuid);

    }

    /**
     * Delete a Questionnaire with technical identifier
     *
     * @param questionnaireUuid questionnaire Uuid
     * @return Nocontent
     */
    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireByUuid(String questionnaireUuid) {
        questionnairePersistencePort.deleteByUuid(questionnaireUuid);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update a existing questionnaire
     *
     * @param aQuestionnaireDto the questionnaire DTO
     * @return the updated questionnaire
     */
    @Override
    public Questionnaire updateQuestionnaire(Questionnaire aQuestionnaireDto, String principal) {
        return questionnairePersistencePort.saveQuestionnaire(aQuestionnaireDto, principal);
    }

    /**
     * Create a  new Questionnaire
     *
     * @param questionnaireDto the questionnaire DTO
     * @return the created questionnaire
     */
    @Override
    public Questionnaire saveQuestionnaire(Questionnaire questionnaireDto, String principal) {
        return questionnairePersistencePort.saveQuestionnaire(questionnaireDto, principal);
    }

    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireUuid(String questionnaireUuid, Pageable pageable) {
        return questionnairePersistencePort.getQuestionsProjectionByQuestionnaireUuid(questionnaireUuid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, String principal) {
        return questionnairePersistencePort.findAllByPage(tagUuid, principal, pageable);
    }

    /**
     * Add  question to a questionnaire
     *
     * @param uuid        questionnaire UUID
     * @param questionDto the question DTO
     * @return QuestionDto
     */
    @Override
    public Question addQuestion(String uuid, Question questionDto, Optional <Integer> position, String principal) {

        return questionnairePersistencePort.addQuestion(uuid, questionDto, position, principal);
    }

    /**
     * Add  questions to a questionnaire
     *
     * @param questionnaireUuid UUI Questionnaire
     * @param questionDtos      List of questions to add
     */
    @Override
    public List <Question> addQuestions(String questionnaireUuid, Collection <Question> questionDtos, String principal) {

        AtomicInteger position = new AtomicInteger(0);
        return questionDtos
                .stream()
                .map(questionDto -> addQuestion(questionnaireUuid, questionDto, Optional.of(position.incrementAndGet()), principal))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(String questionnaireUuid, String questionUuid) {
        questionnairePersistencePort.deleteQuestion(questionnaireUuid, questionUuid);
    }

    @Override
    public void activateQuestionnaire(String questionnaireUuid){

    }

}
