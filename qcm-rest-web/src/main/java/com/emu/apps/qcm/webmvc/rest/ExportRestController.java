package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.adapters.ExportServiceAdapter;
import com.emu.apps.qcm.reporting.ReportService;
import com.emu.apps.qcm.reporting.TypeReport;
import com.emu.apps.qcm.web.dtos.export.ExportDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.EXPORTS;


@RestController
@Profile("webmvc")
@RequestMapping(value = EXPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExportRestController {

    private final ExportServiceAdapter exportService;

    private final ReportService reportService;

    public ExportRestController(ExportServiceAdapter exportService, ReportService reportService) {
        this.exportService = exportService;
        this.reportService = reportService;
    }

    @GetMapping(value = "/{id}")
    @Timer
    @ResponseBody
    public ExportDto getQuestionnaireById(@PathVariable("id") long id) {
        return exportService.getbyQuestionnaireId(id);
    }

    public static final String MS_DOC = "application/msword";

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


    @GetMapping(value = "/{id}/{type}")
    @Timer
    public ResponseEntity <Resource> getReportById(@PathVariable("id") long id, @PathVariable("type") String type) {

        final ExportDto exportDto = exportService.getbyQuestionnaireId(id);

        TypeReport typeReport = TypeReport.getByName(type);

        ByteArrayOutputStream outputStream = reportService.getReportStream(exportDto, typeReport);

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportDto.getName() + "." + type + "\"")
                .body(resource);
    }

}
