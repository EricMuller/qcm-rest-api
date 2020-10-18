package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.ports.ExportBusinessPort;
import com.emu.apps.qcm.spi.reporting.ReportServicePort;
import com.emu.apps.qcm.spi.reporting.services.TypeReport;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Objects;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.EXPORTS;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + EXPORTS)
@Tag(name = "Export")
public class ExportRestController {

    private final ExportBusinessPort exportBusinessPort;

    private final ReportServicePort reportServicePort;

    public ExportRestController(ExportBusinessPort exportService, ReportServicePort reportServicePort) {
        this.exportBusinessPort = exportService;
        this.reportServicePort = reportServicePort;
    }

    @Timer
    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExportDto getExportByQuestionnaireUuid(@PathVariable("uuid") String id) {
        return exportBusinessPort.getbyQuestionnaireUuid(id);
    }


    @Timer
    @GetMapping(value = "/{uuid}/{type-report}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    public ResponseEntity <Resource> getReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        TypeReport typeReport = TypeReport.getByName(type.toUpperCase(Locale.getDefault()));

        if (Objects.isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        final ExportDto exportDto = exportBusinessPort.getbyQuestionnaireUuid(uuid);
        ByteArrayOutputStream outputStream = reportServicePort.getReportStream(exportDto, typeReport);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportDto.getName() + "." + typeReport.getExtention() + "\"")
                .body(resource);
    }


}
