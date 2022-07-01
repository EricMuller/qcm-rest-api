package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.TagQuestionPersistencePort;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    private final TagQuestionPersistencePort tagQuestionPersistencePort;

    public QuestionRepositoryAdapter(QuestionPersistencePort questionPersistencePort, TagQuestionPersistencePort tagQuestionPersistencePort) {
        this.questionPersistencePort = questionPersistencePort;
        this.tagQuestionPersistencePort = tagQuestionPersistencePort;
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
    public Collection <Question> saveQuestions(Collection <Question> questions, final PrincipalId principalId) {
        return questions
                .stream()
                .map(questionDto -> saveQuestion(questionDto, principalId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Question saveQuestion(Question question, PrincipalId principalId) {

        if (Objects.nonNull(question.getResponses())) {
            AtomicLong numberLong = new AtomicLong(0);
            for (Response response : question.getResponses()) {
                response.setNumber(numberLong.incrementAndGet());
            }
        }
        return questionPersistencePort.saveQuestion(question, principalId.toUuid());
    }

    @Override
    public void deleteQuestionOfId(QuestionId questionId) {

        var questionOptional = questionPersistencePort.findByUuid(questionId.toUuid())
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTION, questionId.toUuid()));

        questionPersistencePort.deleteByUuid(questionOptional.getId().toUuid());
    }

    @Override
    public Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        return tagQuestionPersistencePort.findTagsByPage(firstLetter, pageable, principal.toUuid());
    }

    @Override
    public Tag getTagOfId(TagId tagId) {
        return tagQuestionPersistencePort.findByUuid(tagId.toUuid());
    }
}
