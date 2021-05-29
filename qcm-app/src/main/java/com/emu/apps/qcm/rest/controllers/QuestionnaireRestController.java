package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.application.QuestionnaireCatalog;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.QuestionnaireQuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionnaireResources;
import com.emu.apps.qcm.rest.controllers.resources.command.QuestionnaireQuestionUpdate;
import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionnaireView;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emu.apps.qcm.rest.config.cache.CacheName.Names.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static java.util.Optional.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
public class QuestionnaireRestController {


    private final QuestionnaireCatalog questionnaireCatalog;

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;

    public QuestionnaireRestController(QuestionnaireCatalog questionnaireCatalog, QuestionnaireRepository questionnaireServicePort,
                                       QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.questionnaireCatalog = questionnaireCatalog;
        this.questionnaireRepository = questionnaireServicePort;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public QuestionnaireResources getQuestionnaireById(@PathVariable("uuid") String uuid) {
        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.getQuestionnaireById(new QuestionnaireId(uuid))
                .orElseThrow(() -> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTIONNAIRE)));
    }

    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <QuestionnaireResources> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireRepository.deleteQuestionnaireById(new QuestionnaireId(uuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    @PutMapping(value = "/{uuid}")
    @CachePut(cacheNames = QUESTIONNAIRE, condition = "#questionnaireResources != null", key = "#uuid")
    @Timer
    @ResponseBody
    public QuestionnaireResources updateQuestionnaire(@PathVariable("uuid") String uuid,
                                                      @JsonView(QuestionnaireView.Update.class) @RequestBody QuestionnaireResources questionnaireResources) {
        var questionnaire = questionnaireResourcesMapper.questionnaireToModel(uuid, questionnaireResources);
        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.updateQuestionnaire(questionnaire, new PrincipalId(getPrincipal())));
    }

    @PostMapping
    @ResponseBody
    public QuestionnaireResources createQuestionnaire(@JsonView(QuestionnaireView.Create.class) @RequestBody QuestionnaireResources questionnaireResources) {
        var questionnaire = questionnaireResourcesMapper.questionnaireToModel(questionnaireResources);
        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.saveQuestionnaire(questionnaire, new PrincipalId(getPrincipal())));
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam

    public PageQuestionnaireQuestion getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid,
                                                                   @Parameter(hidden = true)
                                                                   @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

        LOGGER.warn("getQuestionsByQuestionnaireUuid");
        var questionnaireQuestionResources =
                questionnaireResourcesMapper.questionnaireQuestionToResources(questionnaireRepository.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable));

        return new PageQuestionnaireQuestion(questionnaireQuestionResources.getContent(), pageable, questionnaireQuestionResources.getContent().size());
    }


    @GetMapping(value = "/{uuid}" + QUESTIONS + "/{q_uuid}")
    @Timer
    @ResponseBody
    public QuestionnaireQuestionResources getQuestionnaireQuestionById(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        return questionnaireResourcesMapper.questionnaireQuestionToResources(questionnaireRepository.getQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public PageQuestionnaire getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                               @Parameter(hidden = true)
                                               @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        var questionnaireResources = questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.getQuestionnaires(tagUuid, pageable, new PrincipalId(getPrincipal())));

        return new PageQuestionnaire(questionnaireResources.getContent(), pageable, questionnaireResources.getContent().size());

    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public QuestionnaireQuestionResources addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody QuestionnaireQuestionUpdate questionnaireQuestionUpdate) {


        QuestionnaireQuestion questionnaireQuestion = questionnaireCatalog.addQuestion(new QuestionnaireId(uuid)
                , new QuestionId(questionnaireQuestionUpdate.getUuid()), of(questionnaireQuestionUpdate.getPosition())
                , new PrincipalId(getPrincipal()));

        return questionnaireResourcesMapper.questionnaireQuestionToResources(questionnaireQuestion);
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireRepository.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    final class PageQuestionnaireQuestion extends PageImpl <QuestionnaireQuestionResources> {
        public PageQuestionnaireQuestion(List <QuestionnaireQuestionResources> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

    final class PageQuestionnaire extends PageImpl <QuestionnaireResources> {
        public PageQuestionnaire(List <QuestionnaireResources> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }


}
