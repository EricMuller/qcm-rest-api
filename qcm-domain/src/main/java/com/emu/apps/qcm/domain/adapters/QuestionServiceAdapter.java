package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.question.QuestionTagsDto;
import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.infrastructure.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.ports.QuestionPersistencePort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Question Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class QuestionServiceAdapter implements QuestionServicePort {

    private final QuestionPersistencePort questionPersistencePort;


    public QuestionServiceAdapter(QuestionPersistencePort questionPersistencePort) {
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Iterable <QuestionTagsDto> getQuestions(String[] tagUuid,
                                                   String[] questionnaireUuid,
                                                   Pageable pageable, String principal) {

        return questionPersistencePort.findAllByPage(questionnaireUuid, tagUuid, pageable, principal);
    }


    @Override
    public QuestionDto getQuestionByUuId(String uuid) {
        return questionPersistencePort.findByUuid(uuid);
    }

    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto, String principal) {
        return questionPersistencePort.saveQuestion(questionDto, principal);
    }

    @Override
    @Transactional
    public Collection <QuestionDto> saveQuestions(Collection <QuestionDto> questionDtos, final String principal) {

        return questionDtos
                .stream()
                .map(questionDto -> saveQuestion(questionDto, principal))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionDto saveQuestion(QuestionDto questionDto, String principal) {

        return questionPersistencePort.saveQuestion(questionDto, principal);

        //fixme: em
//        Question newQuestion = questionMapper.dtoToModel(questionDto);
//
//        CategoryDto categoryDto = questionDto.getCategory();
//        if (Objects.nonNull(categoryDto)) {
//
//            Category category = categoryDOService.findOrCreateByLibelle(
//                    categoryDto.getUserId(), Type.valueOf(categoryDto.getType()), categoryDto.getLibelle());
//
//            newQuestion.setCategory(category);
//        }
//
//        Question question = questionDOService.saveQuestion(newQuestion);
//
//        Iterable <QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());
//
//        question = questionTagDOService.saveQuestionTags(question.getId(), questionTags, principal);
//
//        return questionMapper.modelToDto(question);


    }

    @Override
    public void deleteQuestionByUuid(String uuid) {
        var questionOptional = questionPersistencePort.findByUuid(uuid);
        RaiseExceptionUtil.raiseIfNull(uuid, questionOptional, MessageSupport.UNKNOWN_UUID_QUESTION);
        questionPersistencePort.deleteByUuid(uuid);
    }

}
