package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Response;
import com.emu.apps.qcm.domain.models.Tag;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.domain.repositories.QuestionRepository;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.infra.persistence.exceptions.RaiseExceptionUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.infra.persistence.exceptions.RaiseExceptionUtil.raiseIfNull;

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
    public Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                                String[] questionnaireUuid,
                                                Pageable pageable, String principal) {

        return questionPersistencePort.findAllByPage(questionnaireUuid, tagUuid, pageable, principal);
    }


    @Override
    public Optional <Question> getQuestionByUuId(String uuid) {
        return questionPersistencePort.findByUuid(uuid);
    }

    @Override
    public Question updateQuestion(Question questionDto, String principal) {


        return questionPersistencePort.saveQuestion(questionDto, principal);
    }

    @Override
    @Transactional
    public Collection <Question> saveQuestions(Collection <Question> questionDtos, final String principal) {
        return questionDtos
                .stream()
                .map(questionDto -> saveQuestion(questionDto, principal))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Question saveQuestion(Question questionDto, String principal) {


        if (Objects.nonNull(questionDto.getResponses())) {
            AtomicLong numberLong = new AtomicLong(0);
            for (Response responseDto : questionDto.getResponses()) {
                responseDto.setNumber(numberLong.incrementAndGet());
            }
        }
        return questionPersistencePort.saveQuestion(questionDto, principal);
    }

    @Override
    public void deleteQuestionByUuid(String uuid) {
        var questionOptional = questionPersistencePort.findByUuid(uuid);

        raiseIfNull(uuid, questionOptional, MessageSupport.UNKNOWN_UUID_QUESTION);
        questionPersistencePort.deleteByUuid(uuid);
    }

    public Iterable <Tag> findAllQuestionTagByPage(Pageable pageable, String principal) {
        return questionPersistencePort.findAllTagByPage(pageable, principal);
    }

    public Iterable <String> findAllStatusByPage(Pageable pageable, String principal){

        return questionPersistencePort.findAllStatusByPage(principal,pageable );

    }

}
