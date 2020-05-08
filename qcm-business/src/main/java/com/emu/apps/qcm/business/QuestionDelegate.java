package com.emu.apps.qcm.business;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.services.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.mappers.QuestionMapper;
import com.emu.apps.qcm.web.mappers.QuestionTagMapper;
import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;


/**
 *
 */
@Service
public class QuestionDelegate {

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    private final QuestionTagService questionTagService;

    private final QuestionTagMapper questionTagMapper;

    @Autowired
    public QuestionDelegate(QuestionService questionService, QuestionMapper questionMapper,
                            QuestionTagService questionTagService, QuestionTagMapper questionTagMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.questionTagService = questionTagService;
        this.questionTagMapper = questionTagMapper;
    }


    @Timer
    public Iterable <QuestionTagsDto> getQuestions(Long[] tagIds,
                                                   Long[] questionnaireIds,
                                                   Pageable pageable, Principal principal) {

        var questionSpecificationBuilder = new QuestionSpecificationBuilder();

        questionSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        questionSpecificationBuilder.setQuestionnaireIds(questionnaireIds);
        questionSpecificationBuilder.setTagIds(tagIds);

        return questionMapper.pageToPageTagDto(questionService.findAllByPage(questionSpecificationBuilder.build(), pageable));
    }


    public QuestionDto getQuestionById(long id) {
        return questionMapper.modelToDto(questionService.findById(id).orElse(null));
    }


    public QuestionDto updateQuestion(QuestionDto questionDto, Principal principal) {

        var question = questionService.findById(questionDto.getId()).orElse(null);

        question = questionService.saveQuestion(questionMapper.dtoToModel(question, questionDto));

        var questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);
    }


    public QuestionDto saveQuestion(QuestionDto questionDto, Principal principal) {

        Question question = questionService.saveQuestion(questionMapper.dtoToModel(questionDto));

        Iterable <QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);

    }

    public void deleteQuestionById(long id) {
        var questionOptional = questionService.findById(id);
        EntityExceptionUtil.assertIsPresent(questionOptional, String.valueOf(id));
        questionService.deleteById(id);
    }

}
