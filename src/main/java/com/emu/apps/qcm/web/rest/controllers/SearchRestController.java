package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.web.rest.SearchRestApi;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class SearchRestController implements SearchRestApi {

    public static final String URL = "/search";

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRestController.class);

    @Override
    public Iterable <QuestionDto> searchQuestionsByCriteria(String libelle) {
        return Lists.newArrayList();
    }


//    @ApiOperation(value = "Find suggestions By Domain ", responseContainer = "List", response = SuggestionDto.class, nickname = "getSuggestions", tags = "Search")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved list"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//    }
//    )
//    @RequestMapping(value = "/suggest", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    @PreAuthorize("true")
//    public Iterable<SuggestionDto> getSuggestions(@RequestParam ("domain") final String domainStr, @RequestParam("queryText") String queryText) {
//        final List<SuggestionDto> suggestions = Lists.newArrayList();
//        if (StringUtils.isNoneEmpty(queryText, domainStr)) {
//            Domain domain = Domain.valueOf(domainStr);
//            switch (domain) {
//                case TAG:
//                    suggestMapper.tagsToDtos(tagService.findByLibelleContaining(queryText), Domain.TAG).forEach(suggestions::add);
//                    break;
//                case QUESTIONNAIRE:
//                    suggestMapper.modelsToSuggestDtos(questionnaireService.findByTitleContaining(queryText), Domain.QUESTIONNAIRE).forEach(suggestions::add);
//            }
//        }
//        return suggestions;
//    }
}
