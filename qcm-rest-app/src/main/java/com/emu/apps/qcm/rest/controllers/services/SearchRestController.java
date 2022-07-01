package com.emu.apps.qcm.rest.controllers.services;

import com.emu.apps.qcm.application.QuestionService;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.rest.controllers.services.hal.SearchQuestionModelAssembler;
import com.emu.apps.qcm.rest.controllers.services.search.SearchQuestionsByTagAndQcmId;
import com.emu.apps.qcm.rest.controllers.services.mappers.SearchMapper;
import com.emu.apps.shared.annotations.Timer;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.SEARCH;
import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.SERVICES_API;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping(value = SERVICES_API + SEARCH, produces = {APPLICATION_JSON_VALUE, HAL_JSON_VALUE})
@io.swagger.v3.oas.annotations.tags.Tag(name = "Search")
@Validated
@Timed("Search")
public class SearchRestController {

    static class Actions {
        static final String SEARCH_QUESTIONS_BY_TAG_AND_QCM_ID = "searchQuestionsByTagAndQcmId";
    }

    private final QuestionService questionService;

    private final SearchMapper searchMapper;

    private final SearchQuestionModelAssembler searchQuestionModelAssembler;


    public SearchRestController(QuestionService questionService, SearchMapper searchMapper,
                                SearchQuestionModelAssembler searchQuestionModelAssembler) {
        this.questionService = questionService;
        this.searchMapper = searchMapper;
        this.searchQuestionModelAssembler = searchQuestionModelAssembler;

    }


    @GetMapping(value = SERVICES_API + SEARCH + "/actions/" + Actions.SEARCH_QUESTIONS_BY_TAG_AND_QCM_ID + "/invoke", produces = APPLICATION_JSON_VALUE)
    @Timer
    @PageableAsQueryParam
    @Operation(summary = "Search Question with tagId and Qcm Id", description = "", tags = {"Search"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Questions",
                    content = @Content(array = @ArraySchema(schema = @Schema(name = "SearchQuestionResources", implementation = SearchQuestionsByTagAndQcmId.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "services.search." + Actions.SEARCH_QUESTIONS_BY_TAG_AND_QCM_ID, longTask = true)
    public PagedModel <EntityModel <SearchQuestionsByTagAndQcmId>> searchQuestions(@RequestParam(value = "tag_uuid", required = false) String[] tagUuid,
                                                                                   @RequestParam(value = "questionnaire_uuid", required = false) String[] questionnaireUuid,
                                                                                   @Parameter(hidden = true) @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable,
                                                                                   @Parameter(hidden = true) PagedResourcesAssembler <SearchQuestionsByTagAndQcmId> pagedResourcesAssembler) {
        var page =
                searchMapper.pageQuestionTagsToResources(
                        questionService.findQuestions(tagUuid, questionnaireUuid, pageable, PrincipalId.of(getPrincipal())));

        return pagedResourcesAssembler.toModel(page, this.searchQuestionModelAssembler);
    }


}
