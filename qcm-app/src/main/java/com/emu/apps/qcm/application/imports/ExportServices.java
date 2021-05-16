package com.emu.apps.qcm.application.imports;


import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;

import com.emu.apps.qcm.infra.reporting.model.Export;
import com.emu.apps.qcm.rest.controllers.mappers.ExportMapper;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ExportServices {

    private final ExportMapper exportMapper;

    private final QuestionnaireRepository questionnaireRepository;

    public ExportServices(ExportMapper exportMapper, QuestionnaireRepository questionnaireRepository) {
        this.exportMapper = exportMapper;

        this.questionnaireRepository = questionnaireRepository;
    }


    public Export getbyQuestionnaireUuid(String uuid) {

        var questionnaire = questionnaireRepository.getQuestionnaireById(new QuestionnaireId(uuid))
                .orElseThrow(() -> new EntityNotFoundException(uuid, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));

        var questions = questionnaireRepository.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid));

        return exportMapper.modelToExportDto(questionnaire, questions, generateName(questionnaire));

    }

    String generateName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }

}
