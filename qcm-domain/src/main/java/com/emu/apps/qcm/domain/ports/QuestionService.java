package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;

public interface QuestionService {
    @Timer
    Iterable <QuestionTagsDto> getQuestions(Long[] tagIds,
                                            Long[] questionnaireIds,
                                            Pageable pageable, Principal principal);

    QuestionDto getQuestionById(long id);

    QuestionDto updateQuestion(QuestionDto questionDto, Principal principal);

    @Transactional
    Collection <QuestionDto> saveQuestions(Collection <QuestionDto> questionDtos, Principal principal);

    @Transactional
    QuestionDto saveQuestion(QuestionDto questionDto, Principal principal);

    void deleteQuestionById(long id);
}
