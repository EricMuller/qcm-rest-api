package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;

import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final QuestionnaireIdMapper questionnaireIdMapper;

    private final TagIdMapper tagIdMapper;


    public QuestionRepositoryAdapter(QuestionPersistencePort questionPersistencePort, QuestionnaireIdMapper questionnaireIdMapper, TagIdMapper tagIdMapper) {
        this.questionPersistencePort = questionPersistencePort;
        this.questionnaireIdMapper = questionnaireIdMapper;
        this.tagIdMapper = tagIdMapper;
    }

    @Override
    public Page <QuestionWithTagsOnly> getQuestions(TagId[] tagIds,
                                                    QuestionnaireId[] questionnaireIds,
                                                    Pageable pageable, PrincipalId principal) {

        return questionPersistencePort.findAllByPage(questionnaireIdMapper.toUuid(questionnaireIds), tagIdMapper.toUuid(tagIds), pageable, principal.toUuid());
    }

    @Override
    public Optional <Question> getQuestionById(QuestionId questionId) {
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
    public void deleteQuestionById(QuestionId questionId) {

        var questionOptional = questionPersistencePort.findByUuid(questionId.toUuid())
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, questionId.toUuid()));

        questionPersistencePort.deleteByUuid(questionOptional.getId().toUuid());
    }

    public Page <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal) {
        return questionPersistencePort.findAllTagByPage(pageable, principal.toUuid());
    }

    public Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal) {

        return questionPersistencePort.findAllStatusByPage(principal.toUuid(), pageable);

    }

}
