package com.emu.apps.qcm.application.export;

import java.util.Arrays;

public enum ExportFormat {
    DOCX("docx"), PDF("pdf"), JSON("json");

    private final String extension;

    ExportFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static ExportFormat getFileFormat(String type) {
        return Arrays.stream(values())
                .filter(typeReport -> typeReport.name().equals(type))
                .findFirst().orElse(null);
    }
}
