package com.emu.apps.qcm.application.reporting.template;

import java.util.Arrays;

public enum FileFormat {
    DOCX("docx"), PDF("pdf"), JSON("json");

    private String extention;

    FileFormat(String extention) {
        this.extention = extention;
    }

    public String getExtention() {
        return extention;
    }

    public static FileFormat getFileFormat(String type) {
        return Arrays.stream(values())
                .filter(typeReport -> typeReport.name().equals(type))
                .findFirst().orElse(null);
    }
}
