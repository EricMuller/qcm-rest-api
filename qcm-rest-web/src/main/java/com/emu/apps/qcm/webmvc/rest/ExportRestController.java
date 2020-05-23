package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.services.ExportService;
import com.emu.apps.qcm.web.dtos.export.ExportDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.EXPORTS;


@RestController
@Profile("webmvc")
@RequestMapping(value = EXPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExportRestController {

    private final ExportService reportService;

    public ExportRestController(ExportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/{id}")
    @Timer
    @ResponseBody
    public ExportDto getQuestionnaireById(@PathVariable("id") long id) {
        return reportService.getbyQuestionnaireId(id);
    }

}
