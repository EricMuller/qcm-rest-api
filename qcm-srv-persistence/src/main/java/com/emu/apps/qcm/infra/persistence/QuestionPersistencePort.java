package com.emu.apps.qcm.infra.persistence;


import com.emu.apps.qcm.domain.models.Question;
import com.emu.apps.qcm.domain.models.Tag;
import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface QuestionPersistencePort {

    Optional <Question> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Question saveQuestion(Question questionDto, @NotNull String principal);

    Page <QuestionTags> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal);

    Iterable <QuestionnaireQuestionEntity> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid);

    Iterable <Tag> findAllTagByPage(Pageable pageable, String principal);

}
