package com.emu.apps.qcm.infra.persistence;


import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface QuestionPersistencePort {

    Optional <Question> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Question saveQuestion(Question questionDto, @NotNull String principal);

    Page <QuestionTags> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal);

    Iterable <Tag> findAllTagByPage(Pageable pageable, String principal);

    Iterable <String> findAllStatusByPage(String principal, Pageable pageable);

    Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid);
}
