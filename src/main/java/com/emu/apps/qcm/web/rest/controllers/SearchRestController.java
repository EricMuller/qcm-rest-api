package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.web.rest.SearchRestApi;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class SearchRestController implements SearchRestApi {

    public static final String URL = "/search";

    @Override
    public Iterable <QuestionDto> searchQuestionsByCriteria(String libelle) {
        return Lists.newArrayList();
    }


}
