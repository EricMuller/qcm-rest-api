package com.emu.apps.qcm.spi.reporting;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.spi.reporting.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 *
 */
@Service
@Slf4j
public class ReportServiceAdapter implements ReportServicePort {

    private final ReportServicePdf reportServicePdf;

    private final ReportServiceWord reportServiceWord;

    private final ReportServiceJson reportServiceJson;

    public ReportServiceAdapter(ReportServicePdf reportServicePdf, ReportServiceWord reportServiceWord, ReportServiceJson reportServiceJson) {
        this.reportServicePdf = reportServicePdf;
        this.reportServiceWord = reportServiceWord;
        this.reportServiceJson = reportServiceJson;
    }

    @Override
    public ByteArrayOutputStream getReportStream(ExportDto exportDataDto, TypeReport typeReport) {

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
            default:
                break;
        }
        throw new IllegalArgumentException(typeReport.name());
    }

}
