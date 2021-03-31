package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.models.export.v1.Export;
import com.emu.apps.qcm.infra.persistence.ExportPersistencePort;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.infra.persistence.mappers.exports.ExportMapper;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.springframework.stereotype.Service;

@Service
public class ExportPersistenceAdapter implements ExportPersistencePort {

    private final ExportMapper exportMapper;

    private final QuestionPersistencePort questionPersistencePort;

    private final QuestionnairePersistencePort questionnairePersistencePort;

    public ExportPersistenceAdapter(ExportMapper exportMapper, QuestionPersistencePort questionPersistencePort, QuestionnairePersistencePort questionnairePersistencePort) {
        this.exportMapper = exportMapper;
        this.questionPersistencePort = questionPersistencePort;
        this.questionnairePersistencePort = questionnairePersistencePort;
    }

    @Override
    public Export getExportbyQuestionnaireUuid(String uuid) {

        var questionnaire = questionnairePersistencePort.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));

        var questions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);

        return exportMapper.modelToExportDto(questionnaire, questions, generateName(questionnaire));

    }

}
