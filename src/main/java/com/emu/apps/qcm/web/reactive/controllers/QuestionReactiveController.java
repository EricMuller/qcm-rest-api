package com.emu.apps.qcm.web.reactive.controllers;

import com.emu.apps.qcm.services.QuestionService;
import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.MessageDto;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.mappers.QuestionMapper;
import com.emu.apps.qcm.web.mappers.QuestionTagMapper;
import com.emu.apps.qcm.web.reactive.QuestionReactiveApi;
import com.emu.apps.shared.metrics.Timer;
import com.emu.apps.shared.web.rest.exceptions.utils.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class QuestionReactiveController implements QuestionReactiveApi {

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    private final QuestionTagService questionTagService;

    private final QuestionTagMapper questionTagMapper;

    @Autowired
    public QuestionReactiveController(QuestionService questionService, QuestionMapper questionMapper,
                                      QuestionTagService questionTagService, QuestionTagMapper questionTagMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.questionTagService = questionTagService;
        this.questionTagMapper = questionTagMapper;
    }

    @Override
    @Timer
    public Flux<Iterable<QuestionTagsDto>> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                        @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                                        Pageable pageable, Principal principal) {

        QuestionSpecificationBuilder questionSpecificationBuilder = new QuestionSpecificationBuilder();

        questionSpecificationBuilder.setPrincipal(principal.getName());
        questionSpecificationBuilder.setQuestionnaireIds(questionnaireIds);
        questionSpecificationBuilder.setTagIds(tagIds);

        return Flux.just(questionMapper.pageToPageTagDto(questionService.findAllByPage(questionSpecificationBuilder.build(), pageable)));
    }


    @Override
    public Mono<QuestionDto> getQuestionById(@PathVariable("id") long id) {
        return Mono.just(questionMapper.modelToDto(questionService.findById(id).orElse(null)));
    }

    @Override
    public Mono<QuestionDto> updateQuestion(@RequestBody @Valid final QuestionDto questionDto, Principal principal) {

        Mono<Question> questionMono = Mono.just(questionService.findById(questionDto.getId()).orElse(null));

        return questionMono.map(
                question -> {
                    Question q = questionService.saveQuestion(questionMapper.dtoToModel(question, questionDto));
                    Iterable<QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());
                    return questionMapper.modelToDto(questionTagService.saveQuestionTags(question.getId(), questionTags, principal));
                });

    }

    @Override
    public Mono<QuestionDto> saveQuestion(@RequestBody QuestionDto questionDto, Principal principal) {

        Mono<Question> question = Mono.justOrEmpty(questionService.saveQuestion(questionMapper.dtoToModel(questionDto)));

        return question.map(question1 -> {
            Iterable<QuestionTag> questionTags = questionTagMapper.dtosToModels(questionDto.getQuestionTags());
            return questionMapper.modelToDto(questionTagService.saveQuestionTags(question1.getId(), questionTags, principal));
        });
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity<MessageDto> handleAllException(Exception e) {
        return ResponseEntity.badRequest().body(new MessageDto(e.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Question>> deleteQuestionnaireById(@PathVariable("id") long id) {

        return Mono.just(questionService.findById(id))
                .map(
                        question -> {
                            ExceptionUtil.assertIsPresent(question, String.valueOf(id));
                            questionService.deleteById(id);
                            return ResponseEntity.noContent().build();
                        }
                );


    }

}
