package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Question Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class QuestionRepositoryAdapter implements QuestionRepository {

    private final QuestionPersistencePort questionPersistencePort;


    public QuestionRepositoryAdapter(QuestionPersistencePort questionPersistencePort) {
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Page <QuestionTags> getQuestions(String[] tagUuid,
                                            String[] questionnaireUuid,
                                            Pageable pageable, PrincipalId principal) {

        return questionPersistencePort.findAllByPage(questionnaireUuid, tagUuid, pageable, principal.toUUID());
    }

    @Override
    public Optional <Question> getQuestionById(QuestionId questionId) {
        return questionPersistencePort.findByUuid(questionId.toUUID());
    }

    @Override
    public Question updateQuestion(Question question, PrincipalId principal) {
        return questionPersistencePort.saveQuestion(question, principal.toUUID());
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
            for (Response responseDto : question.getResponses()) {
                responseDto.setNumber(numberLong.incrementAndGet());
            }
        }

        return questionPersistencePort.saveQuestion(question, principal.toUUID());

    }

    @Override
    public void deleteQuestionById(QuestionId questionId) {

        var questionOptional = questionPersistencePort.findByUuid(questionId.toUUID())
                .orElseThrow(() -> new EntityNotFoundException(questionId.toUUID(), MessageSupport.UNKNOWN_UUID_QUESTION));

        questionPersistencePort.deleteByUuid(questionOptional.toUUID());
    }

    public Iterable <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal) {
        return questionPersistencePort.findAllTagByPage(pageable, principal.toUUID());
    }

    public Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal){

        return questionPersistencePort.findAllStatusByPage(principal.toUUID(),pageable );

    }

}
