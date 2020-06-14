package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.question.QuestionTagsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;

public interface QuestionServicePort {
    Iterable <QuestionTagsDto> getQuestions(String[] tagUuid,
                                            String[] questionnaireUuid,
                                            Pageable pageable, Principal principal);

    QuestionDto getQuestionByUuId(String id);

    QuestionDto updateQuestion(QuestionDto questionDto, Principal principal);

    @Transactional
    Collection <QuestionDto> saveQuestions(Collection <QuestionDto> questionDtos, Principal principal);

    @Transactional
    QuestionDto saveQuestion(QuestionDto questionDto, Principal principal);

    void deleteQuestionByUuid(String id);
}
