package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.MessageResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionTagsResources;
import com.emu.apps.qcm.rest.controllers.resources.TagResources;
import com.emu.apps.qcm.rest.controllers.resources.command.QuestionStatus;
import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.validators.ValidUuid;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.emu.apps.qcm.rest.config.cache.CacheName.Names.QUESTION;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.qcm.rest.controllers.resources.MessageResources.ERROR;
import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_QUESTION;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONS, produces = APPLICATION_JSON_VALUE)
@io.swagger.v3.oas.annotations.tags.Tag(name = "Question")
@Validated
public class QuestionRestController {

    private final QuestionRepository questionRepository;

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;

    public QuestionRestController(QuestionRepository questionRepository, QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.questionRepository = questionRepository;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public PageQuestionTags getQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                         @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                         @Parameter(hidden = true)
                                                         @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        var questionTagsPage =
                questionnaireResourcesMapper.questionTagsToResources(
                        questionRepository.getQuestions(tagUuid, questionnaireUuid, pageable, new PrincipalId(getPrincipal())));

        return new PageQuestionTags(questionTagsPage.getContent(), pageable, questionTagsPage.getContent().size());

    }

    @GetMapping(value = TAGS)
    @Timer
    @PageableAsQueryParam
    public Iterable <TagResources> getTags(@Parameter(hidden = true) Pageable pageable) {
        return questionnaireResourcesMapper.tagsToResources(questionRepository.findAllQuestionTagByPage(pageable, new PrincipalId(getPrincipal())));
    }

    @GetMapping(value = STATUS)
    @Timer
    @PageableAsQueryParam
    public Iterable <String> getStatus(@Parameter(hidden = true) Pageable pageable) {
        return questionRepository.findAllStatusByPage(pageable, new PrincipalId(getPrincipal()));
    }


    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTION, key = "#uuid")
    @ResponseBody
    public QuestionResources getQuestionByUuid(@ValidUuid @PathVariable("uuid") String uuid) {
        return questionnaireResourcesMapper.questionToResources(questionRepository.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTION)));
    }

    @PutMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @CachePut(cacheNames = QUESTION, condition = "#questionResources != null", key = "#uuid")
    @ResponseBody
    public QuestionResources updateQuestion(@ValidUuid @PathVariable("uuid") String uuid,
                                            @JsonView(QuestionView.Update.class) @RequestBody @Valid QuestionResources questionResources) {
        var question = questionnaireResourcesMapper.questionToModel(uuid, questionResources);
        return questionnaireResourcesMapper.questionToResources(questionRepository.updateQuestion(question, new PrincipalId(getPrincipal())));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionResources createQuestion(@JsonView(QuestionView.Create.class) @RequestBody @Valid QuestionResources questionResources) {
        var question = questionnaireResourcesMapper.questionToModel(questionResources);
        return questionnaireResourcesMapper.questionToResources(questionRepository.saveQuestion(question, new PrincipalId(getPrincipal())));
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")
    public ResponseEntity <Void> deleteQuestionByUuid(@PathVariable("uuid") String uuid) {
        questionRepository.deleteQuestionById(new QuestionId(uuid));
        return noContent().build();
    }


    @PatchMapping(value = "/{uuid}")
    @ResponseBody
    @CacheEvict(cacheNames = QUESTION, condition = "#uuid != null", key = "#uuid")
    public QuestionResources patchQuestionByUuid(@PathVariable("uuid") String uuid, @RequestBody QuestionStatus questionStatus) {

        var question = questionRepository.getQuestionById(new QuestionId(uuid))
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTION));

        question.setStatus(questionStatus.getStatus());
        return questionnaireResourcesMapper.questionToResources(questionRepository.saveQuestion(question, new PrincipalId(getPrincipal())));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResources> handleAllException(Exception e) {
        return badRequest().body(new MessageResources(ERROR, e.getMessage()));
    }

    final class PageQuestionTags extends PageImpl <QuestionTagsResources> {
        public PageQuestionTags(List <QuestionTagsResources> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

}
