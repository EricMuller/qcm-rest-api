package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTION;


/**
 * Question Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
class QuestionRepositoryAdapter implements QuestionRepository {

    private final QuestionPersistencePort questionPersistencePort;


    public QuestionRepositoryAdapter(QuestionPersistencePort questionPersistencePort ) {
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Optional <Question> getQuestionOfId(QuestionId questionId) {
        return questionPersistencePort.findByUuid(questionId.toUuid());
    }

    @Override
    public Question updateQuestion(Question question, PrincipalId principal) {
        return questionPersistencePort.updateQuestion(question, principal.toUuid());
    }

    @Override
    @Transactional
    public Collection <Question> saveQuestions(Collection <Question> questions, final PrincipalId principal) {
        return questions
                .stream()
                .map(questionDto -> saveQuestion(questionDto, principal))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Question saveQuestion(Question question, PrincipalId principal) {

        if (Objects.nonNull(question.getResponses())) {
            AtomicLong numberLong = new AtomicLong(0);
            for (Response response : question.getResponses()) {
                response.setNumber(numberLong.incrementAndGet());
            }
        }

        return questionPersistencePort.saveQuestion(question, principal.toUuid());

    }

    @Override
    public void deleteQuestionOfId(QuestionId questionId) {

        var questionOptional = questionPersistencePort.findByUuid(questionId.toUuid())
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, questionId.toUuid()));

        questionPersistencePort.deleteByUuid(questionOptional.getId().toUuid());
    }


}
