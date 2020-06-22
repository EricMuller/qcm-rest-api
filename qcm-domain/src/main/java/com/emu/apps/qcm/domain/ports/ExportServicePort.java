package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.QuestionnaireDto;
import com.emu.apps.qcm.dtos.export.ExportDto;

public interface ExportServicePort {
    ExportDto getbyQuestionnaireUuid(String id);

    default String generateName(QuestionnaireDto questionnaire) {

        return questionnaire.getCategory().getLibelle() + "-" + questionnaire.getTitle();

    }
}
