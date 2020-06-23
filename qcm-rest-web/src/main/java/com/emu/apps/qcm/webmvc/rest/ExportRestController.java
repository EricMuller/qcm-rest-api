package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.ExportServicePort;
import com.emu.apps.qcm.dtos.export.ExportDataDto;
import com.emu.apps.qcm.reporting.ReportServicePort;
import com.emu.apps.qcm.reporting.services.TypeReport;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.EXPORTS;
import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_API;


@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + EXPORTS)
public class ExportRestController {

    private final ExportServicePort exportServicePort;

    private final ReportServicePort reportServicePort;

    public ExportRestController(ExportServicePort exportService, ReportServicePort reportServicePort) {
        this.exportServicePort = exportService;
        this.reportServicePort = reportServicePort;
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    @ResponseBody
    public ExportDataDto getQuestionnaireById(@PathVariable("uuid") String id) {
        return exportServicePort.getbyQuestionnaireUuid(id);
    }


    @GetMapping(value = "/{uuid}/{type-report}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timer
    public ResponseEntity <Resource> getReportById(@PathVariable("uuid") String uuid, @PathVariable("type-report") String type) {

        final ExportDataDto exportDto = exportServicePort.getbyQuestionnaireUuid(uuid);

        TypeReport typeReport = TypeReport.getByName(type.toUpperCase());
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException(type);
        }

        ByteArrayOutputStream outputStream = reportServicePort.getReportStream(exportDto, typeReport);

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportDto.getName() + "." + typeReport.getExtention() + "\"")
                .body(resource);
    }


//    public static final String MS_DOC = "application/msword";
//    @GetMapping(value = "/{id}/docx")
//    @Timer
//    public StreamingResponseBody  getReportById(@PathVariable("id") long id, HttpServletResponse response) {
//
//        final ExportDto exportDto = exportService.getbyQuestionnaireId(id);
//
//        ByteArrayOutputStream byteArrayOutputStream = reportService.getReportAstream(exportDto);
//
//        response.setContentType(MS_DOC);
//        response.setHeader(
//                HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" +"test.docx" + "\"");
//        response.setContentLength(byteArrayOutputStream.size());
//
//        return outputStream -> {
//
//            try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
//                byteArrayOutputStream.writeTo(oos);
//                byteArrayOutputStream.flush();
//            }
//        };
//    }

}
