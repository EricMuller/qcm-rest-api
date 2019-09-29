package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.controllers.ReportRestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + ReportRestController.URL)
public interface ReportRestApi {
    @GetMapping(value = "/query", produces = "application/json")
    @ResponseBody
    Iterable<QuestionDto> queryByName(String name);
}
