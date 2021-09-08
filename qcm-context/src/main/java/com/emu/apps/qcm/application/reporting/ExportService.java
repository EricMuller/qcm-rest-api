package com.emu.apps.qcm.application.reporting;


import com.emu.apps.qcm.application.reporting.dtos.Export;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_QUESTIONNAIRE;

@Service
public class ExportService {

    private final ExportMapper exportMapper;

    private final QuestionnaireRepository questionnaireRepository;

    public ExportService(ExportMapper exportMapper, QuestionnaireRepository questionnaireRepository) {
        this.exportMapper = exportMapper;

        this.questionnaireRepository = questionnaireRepository;
    }


    public Export getbyQuestionnaireUuid(String uuid) {

        var questionnaire = questionnaireRepository.getQuestionnaireById(new QuestionnaireId(uuid))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_QUESTIONNAIRE, uuid));

        var questions = questionnaireRepository.getQuestionsByQuestionnaireId(new QuestionnaireId(uuid));

        return exportMapper.modelToExportDto(questionnaire, questions, generateName(questionnaire));

    }

    String generateName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getCategory()) ? questionnaire.getCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }

}
