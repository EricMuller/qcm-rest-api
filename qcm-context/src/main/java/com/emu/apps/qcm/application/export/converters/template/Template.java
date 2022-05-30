package com.emu.apps.qcm.application.export.converters.template;

public enum Template {
    TEMPLATE_QUESTIONNAIRE("template_questionnaire.docx");

    private String fileName;


    Template(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
