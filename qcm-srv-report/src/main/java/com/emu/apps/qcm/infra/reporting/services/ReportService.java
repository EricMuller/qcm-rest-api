package com.emu.apps.qcm.infra.reporting.services;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;

import java.io.ByteArrayOutputStream;

public interface ReportService {
    ByteArrayOutputStream getReportStream(ExportDto exportDataDto);
}