package com.emu.apps.qcm.application.export.converters.template;


import com.emu.apps.qcm.application.export.ExportFormat;

import java.util.Map;

public interface TemplateConverter {
    byte[] convertToByteArray(Map <String, Object> context, Template template, ExportFormat exportFormat);
}
