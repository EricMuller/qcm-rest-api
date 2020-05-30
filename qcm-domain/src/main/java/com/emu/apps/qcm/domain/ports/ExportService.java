package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.export.ExportDto;

public interface ExportService {
    ExportDto getbyQuestionnaireId(long id);

    default String generateName(Questionnaire questionnaire) {

        return questionnaire.getId() + "-" + questionnaire.getTitle();

    }
}
