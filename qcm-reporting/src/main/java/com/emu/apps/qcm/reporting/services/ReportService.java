package com.emu.apps.qcm.reporting.services;

import com.emu.apps.qcm.dtos.export.ExportDataDto;

import java.io.ByteArrayOutputStream;

public interface ReportService {
    ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto);
}
