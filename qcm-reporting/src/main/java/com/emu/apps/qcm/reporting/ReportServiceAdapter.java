package com.emu.apps.qcm.reporting;

import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;
import com.emu.apps.qcm.reporting.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 *
 */
@Service
@Slf4j
public class ReportServiceAdapter implements ReportServicePort {

    private ReportServicePdf reportServicePdf;

    private ReportServiceWord reportServiceWord;

    private ReportServiceJson reportServiceJson;

    public ReportServiceAdapter(ReportServicePdf reportServicePdf, ReportServiceWord reportServiceWord, ReportServiceJson reportServiceJson) {
        this.reportServicePdf = reportServicePdf;
        this.reportServiceWord = reportServiceWord;
        this.reportServiceJson = reportServiceJson;
    }

    @Override
    public ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto, TypeReport typeReport) {

        ReportService reportService = getReportServiceByType(typeReport);
        return reportService.getReportStream(exportDataDto);
    }

    private ReportService getReportServiceByType(TypeReport typeReport) {

        switch (typeReport) {
            case PDF:
                return reportServicePdf;
            case DOCX:
                return reportServiceWord;
            case JSON:
                return reportServiceJson;
        }
        throw new IllegalArgumentException(typeReport.name());
    }

}
