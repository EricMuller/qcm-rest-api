package com.emu.apps.qcm.application.export;


import com.emu.apps.qcm.application.export.dto.Export;

import java.util.HashMap;
import java.util.Map;

public final class TemplateContextBuilder {

    private TemplateContextBuilder() {
    }


    public static Map <String, Object> createContext(Export export) {
        Map <String, Object> context = new HashMap <>();
        context.put("questions", export.getQuestions());
        context.put("questionnaire", export.getQuestionnaire());
        return context;
    }
}


