package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.dtos.export.ExportDto;
import com.emu.apps.qcm.models.QuestionnaireDto;

import java.util.Objects;

public interface ExportServicePort {
    ExportDto getbyQuestionnaireUuid(String id);

    default String generateName(QuestionnaireDto questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "") + "-" + questionnaire.getTitle();

    }
}
