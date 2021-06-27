package com.emu.apps.qcm.rest.controllers.publicized;


import com.emu.apps.qcm.application.PublishedService;
import com.emu.apps.qcm.rest.mappers.PublishedMapper;
import com.emu.apps.qcm.rest.controllers.publicized.resources.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.controllers.publicized.resources.PushishedQuestionnaireQuestion;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@Profile("webmvc")
@Tag(name = "Questionnaires")
@RequestMapping(value = PUBLICIZED_API+ QUESTIONNAIRES, produces = APPLICATION_JSON_VALUE)
public class QuestionnairePublicRestController {

    private final PublishedService publishedService;

    private final PublishedMapper publishedMapper;

    public QuestionnairePublicRestController(PublishedService publishedService, PublishedMapper publishedMapper) {
        this.publishedService = publishedService;
        this.publishedMapper = publishedMapper;
    }

    @GetMapping(value = QUESTIONNAIRES)
    @Timer
    @ResponseBody
    @PageableAsQueryParam
    public Page <PublishedQuestionnaire> getPublishedQuestionnaires(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {
        return publishedMapper.pageQuestionnaireToPublishedQuestionnaires(publishedService.getPublishedQuestionnaires(pageable));
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}")
    @ResponseBody
    public PublishedQuestionnaire getPublishedQuestionnaireByUuid(@PathVariable("uuid") String uuid) {
        return publishedMapper.questionnaireToPublishedQuestionnaire(publishedService.getPublishedQuestionnaireByUuid(uuid));
    }

    @GetMapping(value = CATEGORIES)
    @ResponseBody
    public Iterable <String> getPublishedCategories() {
        return publishedService.getPublishedCategories();
    }

    @GetMapping(value = TAGS)
    @ResponseBody
    public Iterable <String> getPublishedTags() {
        return publishedService.getPublishedTags();
    }

    @GetMapping(value = QUESTIONNAIRES + "/{uuid}/" + QUESTIONS)
    @ResponseBody
    public Iterable <PushishedQuestionnaireQuestion> getPublishedQuestionByQuestionnaireUuid(@PathVariable("uuid") String uuid) {
        return publishedMapper.questionnaireQuestionsToPublishedDtos(publishedService.getPublishedQuestionsByQuestionnaireUuid(uuid));
    }


}
