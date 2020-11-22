package com.emu.apps.qcm.spi.persistence;


import com.emu.apps.qcm.aggregates.question.QuestionTags;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.aggregates.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionPersistencePort {

    Question findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Question saveQuestion(Question questionDto, String principal);

    Page <QuestionTags> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal);

    Iterable <QuestionnaireQuestionEntity> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid);

}
