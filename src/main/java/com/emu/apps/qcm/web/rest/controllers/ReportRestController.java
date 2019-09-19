package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.web.rest.ReportRestApi;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
public class ReportRestController implements ReportRestApi {

    public static final String URL = "/report";

    @Override
    public Iterable <QuestionDto> queryByName(String libelle) {
        return Lists.newArrayList();
    }


}
