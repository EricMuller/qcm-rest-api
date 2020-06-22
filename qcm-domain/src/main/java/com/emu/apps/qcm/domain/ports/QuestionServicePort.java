package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.question.QuestionTagsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface QuestionServicePort {
    Iterable <QuestionTagsDto> getQuestions(String[] tagUuid,
                                            String[] questionnaireUuid,
                                            Pageable pageable, String principal);

    QuestionDto getQuestionByUuId(String id);

    QuestionDto updateQuestion(QuestionDto questionDto, String principal);

    @Transactional
    Collection <QuestionDto> saveQuestions(Collection <QuestionDto> questionDtos, String principal);

    @Transactional
    QuestionDto saveQuestion(QuestionDto questionDto, String principal);

    void deleteQuestionByUuid(String id);
}
