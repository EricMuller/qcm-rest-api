package com.emu.apps.qcm.application.reporting.template;



import com.emu.apps.qcm.application.reporting.dtos.Export;

import java.util.HashMap;
import java.util.Map;

public class ContextBuilder {

     static Map <String, Object> createContext(Export exportDto) {
        Map <String, Object> context = new HashMap <>();
        context.put("questions", exportDto.getQuestions());
        context.put("questionnaire", exportDto.getQuestionnaire());
        return context;
    }
}


