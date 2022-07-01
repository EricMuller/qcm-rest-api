package com.emu.apps.qcm.infra.persistence;


import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionPersistencePort {

    Optional <Question> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Question updateQuestion(Question questionDto, String principal);

    Question saveQuestion(Question questionDto, String principal);

    Page <Tag> findAllTagByPage(Pageable pageable, String principal);

    Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid);
}
