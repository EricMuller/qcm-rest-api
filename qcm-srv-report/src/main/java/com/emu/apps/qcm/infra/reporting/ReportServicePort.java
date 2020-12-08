package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.infra.reporting.services.TypeReport;

import java.io.ByteArrayOutputStream;

public interface ReportServicePort {
    ByteArrayOutputStream getReportStream(ExportDto exportDto, TypeReport typeReport);
}
