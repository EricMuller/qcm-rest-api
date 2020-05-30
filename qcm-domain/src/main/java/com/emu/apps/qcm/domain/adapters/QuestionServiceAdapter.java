package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.QuestionService;
import com.emu.apps.qcm.infrastructure.ports.CategoryDOService;
import com.emu.apps.qcm.infrastructure.ports.QuestionDOService;
import com.emu.apps.qcm.infrastructure.ports.QuestionTagDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.mappers.QuestionMapper;
import com.emu.apps.qcm.mappers.QuestionTagMapper;
import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;
import java.util.Objects;
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
public class QuestionServiceAdapter implements QuestionService {

    private final QuestionDOService questionDOService;

    private final QuestionMapper questionMapper;

    private final QuestionTagDOService questionTagDOService;

    private final QuestionTagMapper questionTagMapper;

    private final CategoryDOService categoryDOService;


    public QuestionServiceAdapter(QuestionDOService questionDOService, QuestionMapper questionMapper, QuestionTagDOService questionTagDOService, QuestionTagMapper questionTagMapper, CategoryDOService categoryDOService) {
        this.questionDOService = questionDOService;
        this.questionMapper = questionMapper;
        this.questionTagDOService = questionTagDOService;
        this.questionTagMapper = questionTagMapper;
        this.categoryDOService = categoryDOService;
    }

    @Override
    @Timer
    public Iterable <QuestionTagsDto> getQuestions(Long[] tagIds,
                                                   Long[] questionnaireIds,
                                                   Pageable pageable, Principal principal) {

        var questionSpecificationBuilder = new QuestionSpecificationBuilder();

        questionSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        questionSpecificationBuilder.setQuestionnaireIds(questionnaireIds);
        questionSpecificationBuilder.setTagIds(tagIds);

        return questionMapper.pageToPageTagDto(questionDOService.findAllByPage(questionSpecificationBuilder.build(), pageable));
    }





    @Override
    public QuestionDto getQuestionById(long id) {
        return questionMapper.modelToDto(questionDOService.findById(id).orElse(null));
    }


    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto, Principal principal) {

        var question = questionDOService.findById(questionDto.getId()).orElse(null);

        question = questionDOService.saveQuestion(questionMapper.dtoToModel(question, questionDto));

        var questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagDOService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);
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


        Question newQuestion = questionMapper.dtoToModel(questionDto);

        CategoryDto categoryDto = questionDto.getCategory();
        if (Objects.nonNull(categoryDto)) {

            Category category = categoryDOService.findOrCreateByLibelle(
                    categoryDto.getUserId(), Type.valueOf(categoryDto.getType()), categoryDto.getLibelle());

            newQuestion.setCategory(category);
        }

        Question question = questionDOService.saveQuestion(newQuestion);

        Iterable <QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());

        question = questionTagDOService.saveQuestionTags(question.getId(), questionTags, principal);

        return questionMapper.modelToDto(question);

    }

    @Override
    public void deleteQuestionById(long id) {
        var questionOptional = questionDOService.findById(id);
        EntityExceptionUtil.assertIsPresent(questionOptional, String.valueOf(id));
        questionDOService.deleteById(id);
    }

}
