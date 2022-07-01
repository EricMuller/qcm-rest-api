package com.emu.apps.qcm.domain.query.question;

import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.domain.query.question.QuestionQuery;
import com.emu.apps.qcm.domain.query.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.infra.persistence.QuestionReaderPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    private final QuestionReaderPort questionReaderPort;

    private final QuestionnaireIdMapper questionnaireIdMapper;

    private final TagIdMapper tagIdMapper;


    public QuestionQueryAdapter(QuestionReaderPort questionReaderPort, QuestionnaireIdMapper questionnaireIdMapper, TagIdMapper tagIdMapper) {
        this.questionReaderPort = questionReaderPort;
        this.questionnaireIdMapper = questionnaireIdMapper;
        this.tagIdMapper = tagIdMapper;
    }

    @Override
    public Page <QuestionWithTagsOnly> findQuestionsOfTagIdAndQuestionnaireId(TagId[] tagIds,
                                                                              QuestionnaireId[] questionnaireIds,
                                                                              Pageable pageable, PrincipalId principal) {

        return questionReaderPort.findAllByPage(questionnaireIdMapper.toUuid(questionnaireIds), tagIdMapper.toUuid(tagIds), pageable, principal.toUuid());
    }


    public Iterable <String> findAllStatusByPage(Pageable pageable, PrincipalId principal) {

        return questionReaderPort.findAllStatusByPage(principal.toUuid(), pageable);

    }

}
