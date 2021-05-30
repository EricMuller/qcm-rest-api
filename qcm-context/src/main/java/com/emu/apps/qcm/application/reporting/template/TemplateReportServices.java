package com.emu.apps.qcm.application.reporting.template;


import com.emu.apps.qcm.application.reporting.dtos.Export;

public interface TemplateReportServices {
    byte[] convertAsStream(Export exportDto, ReportTemplate reportTemplate, FileFormat reportFormat);
}
