package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Response;
import com.emu.apps.qcm.api.models.question.QuestionTags;
import com.emu.apps.qcm.domain.ports.QuestionBusinessPort;
import com.emu.apps.qcm.spi.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
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
public class QuestionBusinessAdapter implements QuestionBusinessPort {

    private final QuestionPersistencePort questionPersistencePort;


    public QuestionBusinessAdapter(QuestionPersistencePort questionPersistencePort) {
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Iterable <QuestionTags> getQuestions(String[] tagUuid,
                                                String[] questionnaireUuid,
                                                Pageable pageable, String principal) {

        return questionPersistencePort.findAllByPage(questionnaireUuid, tagUuid, pageable, principal);
    }


    @Override
    public Question getQuestionByUuId(String uuid) {
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
        RaiseExceptionUtil.raiseIfNull(uuid, questionOptional, MessageSupport.UNKNOWN_UUID_QUESTION);
        questionPersistencePort.deleteByUuid(uuid);
    }

}
