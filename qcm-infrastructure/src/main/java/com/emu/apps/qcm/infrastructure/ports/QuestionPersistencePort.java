package com.emu.apps.qcm.infrastructure.ports;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.question.QuestionTagsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionPersistencePort {

    QuestionDto findByUuid(String uuid);

    void deleteByUuid(String uuid);

    QuestionDto saveQuestion(QuestionDto questionDto, String principal);

    Page <QuestionTagsDto> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal);

    Page <QuestionDto> getQuestionsProjectionByQuestionnaireUuid(String questionnaireUuid, Pageable pageable);

    Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid);

}
