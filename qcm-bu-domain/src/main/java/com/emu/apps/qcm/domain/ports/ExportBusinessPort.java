package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.api.models.Questionnaire;

import java.util.Objects;

public interface ExportBusinessPort {
    ExportDto getbyQuestionnaireUuid(String id);

    default String generateName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }
}
