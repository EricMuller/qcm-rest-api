package com.emu.apps.qcm.spi.reporting;

import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.spi.reporting.services.TypeReport;

import java.io.ByteArrayOutputStream;

public interface ReportServicePort {
    ByteArrayOutputStream getReportStream(ExportDto exportDto, TypeReport typeReport);
}
