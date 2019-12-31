package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.webmvc.rest.ReportRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class ReportRestController implements ReportRestApi {

    public static final String URL = "/report";

    @Override
    public Iterable <QuestionDto> queryByName(String libelle) {
        return new ArrayList <>();
    }


}
