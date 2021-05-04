package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.QuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionnaireQuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionnaireResources;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.rest.config.cache.CacheName.Names.QUESTIONNAIRE;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static java.util.Optional.empty;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "Questionnaire")
@Slf4j
public class QuestionnaireRestController {

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;

    public QuestionnaireRestController(QuestionnaireRepository questionnaireServicePort,
                                       QuestionnaireResourcesMapper questionnaireResourcesMapper) {
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

    @PutMapping
    @CachePut(cacheNames = QUESTIONNAIRE, condition = "#questionnaireResources != null", key = "#questionnaireResources.uuid")
    @Timer
    @ResponseBody
    public QuestionnaireResources updateQuestionnaire(@RequestBody QuestionnaireResources questionnaireResources) {
        var questionnaire = questionnaireResourcesMapper.questionnaireToModel(questionnaireResources);
        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.updateQuestionnaire(questionnaire, new PrincipalId(getPrincipal())));
    }

    @PostMapping
    @ResponseBody
    public QuestionnaireResources saveQuestionnaire(@RequestBody QuestionnaireResources questionnaireResources) {
        var questionnaire = questionnaireResourcesMapper.questionnaireToModel(questionnaireResources);
        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.saveQuestionnaire(questionnaire, new PrincipalId(getPrincipal())));
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
    public Page <QuestionnaireQuestionResources> getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid,
                                                                               @Parameter(hidden = true)
                                                                               @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

        LOGGER.warn("getQuestionsByQuestionnaireUuid");
        return questionnaireResourcesMapper.questionnaireQuestionToResources(questionnaireRepository.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable));
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
    public Page <QuestionnaireResources> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                           @Parameter(hidden = true)
                                                           @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireResourcesMapper.questionnaireToResources(questionnaireRepository.getQuestionnaires(tagUuid, pageable, new PrincipalId(getPrincipal())));
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public QuestionResources addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody QuestionResources questionResources) {

        var question = questionnaireResourcesMapper.questionToModel(questionResources);
        return questionnaireResourcesMapper.questionToResources(
                questionnaireRepository.addQuestion(new QuestionnaireId(uuid), question, empty(), new PrincipalId(getPrincipal())));
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireRepository.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

}
