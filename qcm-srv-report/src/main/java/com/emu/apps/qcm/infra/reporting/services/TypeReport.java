package com.emu.apps.qcm.infra.reporting.services;

import java.util.Arrays;

public enum TypeReport {
    DOCX("docx"), PDF("pdf"), JSON("json");

    private String extention;

    TypeReport(String extention) {
        this.extention = extention;
    }

    public String getExtention() {
        return extention;
    }

    public static TypeReport getByName(String type) {
        return Arrays.stream(values())
                .filter(typeReport -> typeReport.name().equals(type))
                .findFirst().orElse(null);
    }
}
