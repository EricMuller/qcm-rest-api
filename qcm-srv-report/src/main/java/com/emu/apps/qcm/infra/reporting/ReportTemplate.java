package com.emu.apps.qcm.infra.reporting;

public enum ReportTemplate {
    QUESTIONNAIRE("template_questionnaire.docx");

    private String fileName;


    ReportTemplate(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
