package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.models.Questionnaire;

import java.util.Objects;

public interface ExportRepository {
    ExportDto getbyQuestionnaireUuid(String id);

    default String generateName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }
}
