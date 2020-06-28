package com.emu.apps.qcm.reporting;

import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;
import com.emu.apps.qcm.reporting.services.TypeReport;

import java.io.ByteArrayOutputStream;

public interface ReportServicePort {
    ByteArrayOutputStream getReportStream(ExportDataDto exportDto, TypeReport typeReport);
}
