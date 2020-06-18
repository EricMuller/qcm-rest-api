package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.ports.QuestionPersistencePort;
import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.question.QuestionTagsDto;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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
                                                   Pageable pageable, Principal principal) {

        return questionPersistencePort.findAllByPage(questionnaireUuid, tagUuid, pageable,PrincipalUtils.getEmail(principal));
    }


    @Override
    public QuestionDto getQuestionByUuId(String uuid) {
        return questionPersistencePort.findByUuid(uuid);
    }

    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto, Principal principal) {
        return questionPersistencePort.saveQuestion(questionDto, PrincipalUtils.getEmail(principal));
    }

    @Override
    @Transactional
    public Collection <QuestionDto> saveQuestions(Collection <QuestionDto> questionDtos, final Principal principal) {

        return questionDtos
                .stream()
                .map(questionDto -> saveQuestion(questionDto, principal))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionDto saveQuestion(QuestionDto questionDto, Principal principal) {

        return questionPersistencePort.saveQuestion(questionDto, PrincipalUtils.getEmail(principal));

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
        EntityExceptionUtil.raiseExceptionIfNull(uuid, questionOptional, MessageSupport.UNKNOWN_UUID_QUESTION);
        questionPersistencePort.deleteByUuid(uuid);
    }

}