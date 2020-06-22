package com.emu.apps.qcm.reporting;

import com.emu.apps.qcm.dtos.export.ExportDto;

import java.io.ByteArrayOutputStream;

public interface ReportServicePort {
    ByteArrayOutputStream getReportStream(ExportDto exportDto, TypeReport typeReport);
}
