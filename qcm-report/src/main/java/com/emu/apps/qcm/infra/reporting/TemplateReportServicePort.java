package com.emu.apps.qcm.infra.reporting;


import com.emu.apps.qcm.infra.reporting.model.Export;

public interface TemplateReportServicePort {
    byte[] convertAsStream(Export exportDto, ReportTemplate reportTemplate, FileFormat reportFormat);
}
