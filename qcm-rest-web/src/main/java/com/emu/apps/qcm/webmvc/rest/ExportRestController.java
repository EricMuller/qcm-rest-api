package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.ExportServicePort;
import com.emu.apps.qcm.reporting.ReportServicePort;
import com.emu.apps.qcm.reporting.TypeReport;
import com.emu.apps.qcm.web.dtos.export.ExportDto;
import com.emu.apps.shared.annotations.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.GUEST_EXPORTS;


@RestController
@Profile("webmvc")
@RequestMapping(value = GUEST_EXPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExportRestController {

    private final ExportServicePort exportServicePort;

    private final ReportServicePort reportServicePort;

    public ExportRestController(ExportServicePort exportService, ReportServicePort reportServicePort) {
        this.exportServicePort = exportService;
        this.reportServicePort = reportServicePort;
    }

    @GetMapping(value = "/{uuid}")
    @Timer
    @ResponseBody
    public ExportDto getQuestionnaireById(@PathVariable("uuid") String id) {
        return exportServicePort.getbyQuestionnaireUuid(id);
    }



    @GetMapping(value = "/{uuid}/{type-report}")
    @Timer
    public ResponseEntity <Resource> getReportById(@PathVariable("uuid") String uuid, @PathVariable("type-report") String typeReport) {

        final ExportDto exportDto = exportServicePort.getbyQuestionnaireUuid(uuid);

        ByteArrayOutputStream outputStream = reportServicePort.getReportStream(exportDto, TypeReport.getByName(typeReport));

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportDto.getName() + "." + typeReport + "\"")
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