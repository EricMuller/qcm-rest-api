package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.QuestionnaireDto;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.guest.GuestCategoryDto;
import com.emu.apps.qcm.guest.GuestTagDto;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.ports.QuestionPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.QuestionnairePersistencePort;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Questionnaire Business Delegate
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class QuestionnaireServiceAdapter implements QuestionnaireServicePort {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final QuestionPersistencePort questionPersistencePort;

    public QuestionnaireServiceAdapter(QuestionnairePersistencePort questionnairePersistencePort,
                                       QuestionPersistencePort questionPersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.questionPersistencePort = questionPersistencePort;
    }

    /**
     * Find a Questionnaire with technical identifier
     *
     * @param questionnaireUuid questionnaire uuid
     * @return the questionnaire
     */
    @Override
    @Transactional(readOnly = true)
    public QuestionnaireDto getQuestionnaireByUuid(String questionnaireUuid) {
        var questionnaire = questionnairePersistencePort.findByUuid(questionnaireUuid);
        EntityExceptionUtil.raiseExceptionIfNull(questionnaireUuid, questionnaire, String.valueOf(questionnaireUuid));
        return questionnaire;
    }

    /**
     * Delete a Questionnaire with technical identifier
     *
     * @param questionnaireUuid questionnaire Uuid
     * @return Nocontent
     */
    @Override
    public ResponseEntity <QuestionnaireDto> deleteQuestionnaireByUuid(String questionnaireUuid) {
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
    public QuestionnaireDto updateQuestionnaire(QuestionnaireDto aQuestionnaireDto, Principal principal) {
        return questionnairePersistencePort.saveQuestionnaire(aQuestionnaireDto, PrincipalUtils.getEmail(principal));
    }

    /**
     * Create a  new Questionnaire
     *
     * @param questionnaireDto the questionnaire DTO
     * @return the created questionnaire
     */
    @Override
    public QuestionnaireDto saveQuestionnaire(QuestionnaireDto questionnaireDto, Principal principal) {
        return questionnairePersistencePort.saveQuestionnaire(questionnaireDto, PrincipalUtils.getEmail(principal));
    }

    public Page <QuestionDto> getQuestionsByQuestionnaireUuid(String questionnaireUuid, Pageable pageable) {
        return questionPersistencePort.getQuestionsProjectionByQuestionnaireUuid(questionnaireUuid, pageable);
    }

    /**
     * find  a list of questionnaires
     *
     * @param tagUuid   array of technical tag UUID
     * @param pageable
     * @param principal
     * @return a list of questionnaires with the specified tag
     */
    @Transactional(readOnly = true)
    public Page <QuestionnaireDto> getPublicQuestionnaires(String[] tagUuid, Pageable pageable, String principal) {
        return questionnairePersistencePort.findAllPublicByPage(tagUuid, principal, pageable);
    }

    @Transactional(readOnly = true)
    public Iterable <GuestCategoryDto> getPublicCategories() {
        return questionnairePersistencePort.getPublicCategories();
    }
    @Transactional(readOnly = true)
    public Iterable <GuestTagDto> getPublicTags() {
        return questionnairePersistencePort.getPublicTags();
    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionnaireDto> getQuestionnaires(String[] tagUuid, Pageable pageable, Principal principal) {
        return questionnairePersistencePort.findAllByPage(tagUuid, PrincipalUtils.getEmail(principal), pageable);
    }

    /**
     * Add  question to a questionnaire
     *
     * @param uuid        questionnaire UUID
     * @param questionDto the question DTO
     * @return QuestionDto
     */
    @Override
    public QuestionDto addQuestion(String uuid, QuestionDto questionDto, Optional <Long> position) {

        return questionnairePersistencePort.addQuestion(uuid, questionDto, position);
    }

    /**
     * Add  questions to a questionnaire
     *
     * @param questionnaireUuid UUI Questionnaire
     * @param questionDtos      List of questions to add
     */
    @Override
    public List <QuestionDto> addQuestions(String questionnaireUuid, Collection <QuestionDto> questionDtos) {

        AtomicLong atomicLong = new AtomicLong(0);
        return questionDtos
                .stream()
                .map(questionDto -> addQuestion(questionnaireUuid, questionDto, Optional.of(atomicLong.incrementAndGet())))
                .collect(Collectors.toList());
    }

}
