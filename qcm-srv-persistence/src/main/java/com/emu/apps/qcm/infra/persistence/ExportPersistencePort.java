package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.models.export.v1.Export;
import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;

import java.util.Objects;

public interface ExportPersistencePort {

    Export getExportbyQuestionnaireUuid(String uuid);


    default String generateName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }

}
