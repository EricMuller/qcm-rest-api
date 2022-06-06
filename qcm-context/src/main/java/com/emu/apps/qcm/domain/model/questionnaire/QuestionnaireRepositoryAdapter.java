package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;

/**
 * Questionnaire Business Delegate
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
class QuestionnaireRepositoryAdapter implements QuestionnaireRepository {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    public QuestionnaireRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
    }

    /**
     * Find a Questionnaire with technical identifier
     *
     * @param questionnaireId questionnaire uuid
     * @return the questionnaire
     */
    @Override
    @Transactional(readOnly = true)
    public Optional <Questionnaire> getQuestionnaireOfId(QuestionnaireId questionnaireId) {
        return questionnairePersistencePort.findByUuid(questionnaireId.toUuid());

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsOfId(QuestionnaireId questionnaireId) {
        return questionnairePersistencePort.existsByUuid(questionnaireId.toUuid());
    }

    @Override
    public QuestionnaireAggregate getQuestionnaireAggregateOfId(QuestionnaireId questionnaireId) {

        var questionnaire = questionnairePersistencePort.findByUuid(questionnaireId.toUuid())
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTIONNAIRE, questionnaireId.toUuid()));
        var questionnaireQuestions = Streamable.of(questionnairePersistencePort.getQuestionsByQuestionnaireUuid(questionnaireId.toUuid())).toList();

        return new QuestionnaireAggregate(questionnaire, questionnaireQuestions);
    }

    /**
     * Delete a Questionnaire with technical identifier
     *
     * @param questionnaireId questionnaire Uuid
     * @return Nocontent
     */
    @Override
    public ResponseEntity <Questionnaire> deleteQuestionnaireOfId(QuestionnaireId questionnaireId) {
        questionnairePersistencePort.deleteByUuid(questionnaireId.toUuid());
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
        return questionnairePersistencePort.updateQuestionnaire(questionnaire, principal.toUuid());
    }

    /**
     * Create a  new Questionnaire
     *
     * @param questionnaire the questionnaire DTO
     * @return the created questionnaire
     */
    @Override
    public Questionnaire saveQuestionnaire(Questionnaire questionnaire, PrincipalId principal) {
        return questionnairePersistencePort.saveQuestionnaire(questionnaire, principal.toUuid());
    }

    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireOfId(QuestionnaireId questionnaireId, Pageable pageable) {
        return questionnairePersistencePort.getQuestionsByQuestionnaireUuid(questionnaireId.toUuid(), pageable);
    }

    public Iterable <QuestionnaireQuestion> getQuestionsByQuestionnaireOfId(QuestionnaireId questionnaireId) {
        return questionnairePersistencePort.getQuestionsByQuestionnaireUuid(questionnaireId.toUuid());
    }

    /**
     * Add  question to a questionnaire
     *
     * @param uuid     questionnaire UUID
     * @param question the question DTO
     * @return QuestionDto
     */
    @Override
    public QuestionnaireQuestion addQuestion(QuestionnaireId questionnaireId, QuestionId questionId, Optional <Integer> position, PrincipalId principal) {

        return questionnairePersistencePort.addQuestion(questionnaireId.toUuid(), questionId.toUuid(), position, principal.toUuid());
    }

    /**
     * Add  questions to a questionnaire
     *
     * @param questionnaireId UUI Questionnaire
     * @param questions       List of questions to add
     */
    @Override
    public List <QuestionnaireQuestion> addQuestions(QuestionnaireId questionnaireId, Collection <Question> questions, PrincipalId principal) {

        AtomicInteger position = new AtomicInteger(0);
        return questions
                .stream()
                .map(question-> addQuestion(questionnaireId, question.getId(), Optional.of(position.incrementAndGet()), principal))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        questionnairePersistencePort.deleteQuestion(questionnaireId.toUuid(), questionId.toUuid());
    }

    @Override
    public QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId) {
        return questionnairePersistencePort.getQuestion(questionnaireId.toUuid(), questionId.toUuid());
    }

    @Override
    public void activateQuestionnaire(QuestionnaireId questionnaireId) {
        // on progress
    }

}
