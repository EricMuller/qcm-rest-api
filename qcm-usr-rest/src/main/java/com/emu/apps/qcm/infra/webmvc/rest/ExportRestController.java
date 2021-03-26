package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.repositories.ExportRepository;
import com.emu.apps.qcm.infra.reporting.FileFormat;
import com.emu.apps.qcm.infra.reporting.ReportServicePort;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;

import static com.emu.apps.qcm.infra.reporting.ReportTemplate.TEMPLATE_QUESTIONNAIRE;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.EXPORTS;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + EXPORTS)
@Tag(name = "Export")
public class ExportRestController {

    private final ExportRepository exportRepository;

    private final ReportServicePort reportServicePort;

    @Autowired
    public ExportRestController(ExportRepository exportService, ReportServicePort reportServicePort) {
        this.exportRepository = exportService;
        this.reportServicePort = reportServicePort;
    }

    @Timer
    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExportDto getExportByQuestionnaireUuid(@PathVariable("uuid") String id) {
        return exportRepository.getbyQuestionnaireUuid(id);
    }


    @Timer
    @GetMapping(value = "/{uuid}/{type-report}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    public ResponseEntity <Resource> getReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        if (Objects.isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        FileFormat reportFormat = FileFormat.getByName(type.toUpperCase(Locale.getDefault()));

        final ExportDto exportDto = exportRepository.getbyQuestionnaireUuid(uuid);

        ByteArrayResource resource = new ByteArrayResource(reportServicePort
                .convertAsStream(exportDto, TEMPLATE_QUESTIONNAIRE, reportFormat));

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + exportDto.getName() + "." + reportFormat.getExtention() + "\"")
                .body(resource);
    }


}
