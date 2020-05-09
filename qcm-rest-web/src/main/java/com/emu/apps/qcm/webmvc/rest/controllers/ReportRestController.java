package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.web.dtos.QuestionDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.REPORTS;


/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = REPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportRestController {

    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable <QuestionDto> queryByName(String libelle) {
        return new ArrayList <>();
    }


}
