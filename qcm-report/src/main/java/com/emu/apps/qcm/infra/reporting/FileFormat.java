package com.emu.apps.qcm.infra.reporting;

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

    public static FileFormat getByName(String type) {
        return Arrays.stream(values())
                .filter(typeReport -> typeReport.name().equals(type))
                .findFirst().orElse(null);
    }
}
