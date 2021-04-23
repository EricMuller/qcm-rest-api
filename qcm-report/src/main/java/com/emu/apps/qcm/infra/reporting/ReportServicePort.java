package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.model.export.v1.Export;

public interface ReportServicePort {
    byte[] convertAsStream(Export exportDto, ReportTemplate reportTemplate, FileFormat reportFormat);
}
