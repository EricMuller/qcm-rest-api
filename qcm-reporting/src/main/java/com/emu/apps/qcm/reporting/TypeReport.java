package com.emu.apps.qcm.reporting;

import java.util.Arrays;

public enum TypeReport {
    DOCX, PDF;

    public static TypeReport getByName(String type) {
        return Arrays.stream(values())
                .filter(typeReport -> typeReport.name().equals(type))
                .findFirst().orElse(null);
    }
}
