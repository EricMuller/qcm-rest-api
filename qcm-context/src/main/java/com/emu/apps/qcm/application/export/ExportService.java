package com.emu.apps.qcm.application.export;


import com.emu.apps.qcm.application.export.converters.json.ObjectToJsonConverter;
import com.emu.apps.qcm.application.export.converters.template.TemplateConverter;
import com.emu.apps.qcm.application.export.dto.Export;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.emu.apps.qcm.application.export.ExportFormat.JSON;
import static com.emu.apps.qcm.application.export.converters.template.Template.TEMPLATE_QUESTIONNAIRE;

@Service
public class ExportService {

    private final ExportMapper exportMapper;

    private final QuestionnaireRepository questionnaireRepository;

    private final TemplateConverter templateConverter;

    private final ObjectToJsonConverter objectToJsonConverter;

    public ExportService(ExportMapper exportMapper, QuestionnaireRepository questionnaireRepository, TemplateConverter templateConverter, ObjectToJsonConverter objectToJsonConverter) {
        this.exportMapper = exportMapper;

        this.questionnaireRepository = questionnaireRepository;
        this.templateConverter = templateConverter;
        this.objectToJsonConverter = objectToJsonConverter;
    }


    public Export getExportByQuestionnaireUuid(QuestionnaireId questionnaireId) {

        var questionnaire = questionnaireRepository.getQuestionnaireAggregateOfId(questionnaireId);

        return exportMapper.modelToExportDto(questionnaire.getRoot(), questionnaire.getQuestionnaireQuestions(), getExportName(questionnaire.getRoot()));

    }

    String getExportName(Questionnaire questionnaire) {

        return (Objects.nonNull(questionnaire.getMpttCategory()) ? questionnaire.getMpttCategory().getLibelle() : "")
                + "-" + questionnaire.getTitle() + "-" + questionnaire.getStatus() + "-" + questionnaire.getVersion();

    }

    public ByteArrayResource exportAsByteArray(QuestionnaireId questionnaireId, ExportFormat exportFormat) {

        final Export export = getExportByQuestionnaireUuid(questionnaireId);

        return JSON.equals(exportFormat) ?
                new ByteArrayResource(objectToJsonConverter.convertToByteArray(export)) :
                new ByteArrayResource(templateConverter
                        .convertToByteArray(TemplateContextBuilder.createContext(export), TEMPLATE_QUESTIONNAIRE, exportFormat));

    }
}
