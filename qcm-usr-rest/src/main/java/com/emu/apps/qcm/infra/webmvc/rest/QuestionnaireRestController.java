package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.question.Question;
import com.emu.apps.qcm.domain.models.question.QuestionId;
import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.repositories.QuestionnaireRepository;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.annotations.Timer;
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

import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.*;
import static com.emu.apps.qcm.infra.webmvc.config.cache.CacheName.Names.QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static java.util.Optional.empty;
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

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireRestController(QuestionnaireRepository questionnaireServicePort) {
        this.questionnaireRepository = questionnaireServicePort;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @Cacheable(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public Questionnaire getQuestionnaireById(@PathVariable("uuid") String uuid) {
        return questionnaireRepository.getQuestionnaireById(new QuestionnaireId(uuid))
                .orElseThrow(()-> new EntityNotFoundException(uuid, UNKNOWN_UUID_QUESTIONNAIRE));
    }

    @DeleteMapping(value = "/{uuid}")
    @CacheEvict(cacheNames = QUESTIONNAIRE, key = "#uuid")
    @ResponseBody
    public ResponseEntity <Questionnaire> deleteQuestionnaireById(@PathVariable("uuid") String uuid) {
        questionnaireRepository.deleteQuestionnaireById(new QuestionnaireId(uuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

    @PutMapping
    @CachePut(cacheNames = QUESTIONNAIRE, condition = "#questionnaire != null", key = "#questionnaire.uuid")
    @Timer
    @ResponseBody
    public Questionnaire updateQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return questionnaireRepository.updateQuestionnaire(questionnaire, new PrincipalId(getPrincipal()));
    }

    @PostMapping
    @ResponseBody
    public Questionnaire saveQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return questionnaireRepository.saveQuestionnaire(questionnaire, new PrincipalId(getPrincipal()));
    }

    /* /{id:[\d]+}/questions*/
    @GetMapping(value = "/{uuid}" + QUESTIONS)
    @ResponseBody
    @PageableAsQueryParam
    public Page <QuestionnaireQuestion> getQuestionsByQuestionnaireId(@PathVariable("uuid") String uuid,
                                                                      @Parameter(hidden = true)
                                                                        @PageableDefault(direction = ASC, sort = {"position"}) Pageable pageable) {

        LOGGER.warn("getQuestionsByQuestionnaireUuid");
        return questionnaireRepository.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid), pageable);
    }



    @GetMapping(value = "/{uuid}"+ QUESTIONS +"/{q_uuid}")
    @Timer
    @ResponseBody
    public QuestionnaireQuestion getQuestionnaireQuestionById(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid ) {
        return questionnaireRepository.getQuestion(new QuestionnaireId(uuid),new QuestionId(questionUuid)) ;
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <Questionnaire> getQuestionnaires(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                  @Parameter(hidden = true)
                                                  @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireRepository.getQuestionnaires(tagUuid, pageable, new PrincipalId(getPrincipal()));
    }

    @PutMapping(value = "/{uuid}/questions")
    @ResponseBody
    public Question addQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @RequestBody Question question) {

        // send QuestionnaireQuestionDto
        return questionnaireRepository.addQuestion(new QuestionnaireId(uuid), question, empty(), new PrincipalId(getPrincipal()));
    }


    @DeleteMapping(value = "/{uuid}/questions/{question_uuid}")
    @ResponseBody
    public ResponseEntity <Void> deleteQuestionByQuestionnaireId(@PathVariable("uuid") String uuid, @PathVariable("question_uuid") String questionUuid) {
        questionnaireRepository.deleteQuestion(new QuestionnaireId(uuid), new QuestionId(questionUuid));
        return new ResponseEntity <>(NO_CONTENT);
    }

}
