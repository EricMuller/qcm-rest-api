package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;
import com.emu.apps.qcm.models.QuestionnaireDto;

import java.util.Objects;

public interface ExportServicePort {
    ExportDataDto getbyQuestionnaireUuid(String id);

    default String generateName(QuestionnaireDto questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }
}
