package com.emu.apps.qcm.rest.controllers.secured;


import com.emu.apps.qcm.application.reporting.ExportService;
import com.emu.apps.qcm.application.reporting.dtos.Export;
import com.emu.apps.qcm.application.reporting.template.FileFormat;
import com.emu.apps.qcm.application.reporting.template.TemplateReportServices;
import com.emu.apps.qcm.rest.controllers.ApiRestMappings;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.emu.apps.qcm.application.reporting.template.FileFormat.getFileFormat;
import static com.emu.apps.qcm.application.reporting.template.ReportTemplate.TEMPLATE_QUESTIONNAIRE;
import static java.util.Locale.getDefault;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;


@RestController
@Profile("webmvc")
@RequestMapping(value = ApiRestMappings.PROTECTED_API + ApiRestMappings.EXPORTS)
@Tag(name = "Export")
public class ExportRestController {

    private final ExportService exportService;

    private final TemplateReportServices templateReportServices;

    @Autowired
    public ExportRestController(ExportService exportService, TemplateReportServices templateReportServices) {
        this.exportService = exportService;
        this.templateReportServices = templateReportServices;
    }

    @Timer
    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Export getExportByQuestionnaireUuid(@PathVariable("uuid") String id) {
        return exportService.getbyQuestionnaireUuid(id);
    }


    @Timer
    @GetMapping(value = "/{uuid}/{type-report}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @SuppressWarnings("squid:S2583")
    public ResponseEntity <Resource> getReportByQuestionnaireUuid(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        if (Objects.isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        FileFormat reportFormat = getFileFormat(type.toUpperCase(getDefault()));

        final Export export = exportService.getbyQuestionnaireUuid(uuid);

        ByteArrayResource resource = new ByteArrayResource(templateReportServices
                .convertAsStream(export, TEMPLATE_QUESTIONNAIRE, reportFormat));

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + export.getName() + "." + reportFormat.getExtention() + "\"")
                .body(resource);
    }


}
