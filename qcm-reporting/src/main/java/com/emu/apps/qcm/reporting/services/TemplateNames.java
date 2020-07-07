package com.emu.apps.qcm.reporting.services;

public enum TemplateNames {
    QUESTIONNAIRE("template_questionnaire.docx");

    private String name;

    TemplateNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}