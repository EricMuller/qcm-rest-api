package com.emu.apps.qcm.reporting.services;

import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;

import java.io.ByteArrayOutputStream;

public interface ReportService {
    ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto);
}
