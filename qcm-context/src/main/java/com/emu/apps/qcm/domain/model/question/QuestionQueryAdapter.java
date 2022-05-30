package com.emu.apps.qcm.domain.model.question;

import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
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
class QuestionQueryAdapter implements QuestionQuery {

    private final QuestionPersistencePort questionPersistencePort;

    private final QuestionnaireIdMapper questionnaireIdMapper;

    private final TagIdMapper tagIdMapper;


    public QuestionQueryAdapter(QuestionPersistencePort questionPersistencePort, QuestionnaireIdMapper questionnaireIdMapper, TagIdMapper tagIdMapper) {
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
    public Page <Tag> findAllQuestionTagByPage(Pageable pageable, PrincipalId principal) {
        return questionPersistencePort.findAllTagByPage(pageable, principal.toUuid());
    }

    public Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal) {

        return questionPersistencePort.findAllStatusByPage(principal.toUuid(), pageable);

    }

}
