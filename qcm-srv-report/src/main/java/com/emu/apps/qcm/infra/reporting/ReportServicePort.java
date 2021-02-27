package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;

public interface ReportServicePort {
    byte[] convertAsStream(ExportDto exportDto, ReportTemplate reportTemplate, FileFormat reportFormat);
}
