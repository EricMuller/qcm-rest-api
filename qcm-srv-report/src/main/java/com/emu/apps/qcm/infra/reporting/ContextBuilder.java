package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;

import java.util.HashMap;
import java.util.Map;

public class ContextBuilder {

     static Map <String, Object> createContext(ExportDto exportDto) {
        Map <String, Object> context = new HashMap <>();
        context.put("questions", exportDto.getQuestions());
        context.put("questionnaire", exportDto.getQuestionnaire());
        return context;
    }
}


