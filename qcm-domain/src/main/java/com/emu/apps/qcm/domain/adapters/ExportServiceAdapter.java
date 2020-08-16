package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.ports.ExportServicePort;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.spi.persistence.QuestionnairePersistencePort;
import com.emu.apps.qcm.spi.persistence.mappers.exports.ExportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Questionnaire Business Adapter
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class ExportServiceAdapter implements ExportServicePort {

    private final QuestionnairePersistencePort questionnairePersistencePort;

    private final ExportMapper exportMapper;

    private final QuestionPersistencePort questionPersistencePort;

    public ExportServiceAdapter(QuestionnairePersistencePort questionnairePersistencePort, ExportMapper exportMapper, QuestionPersistencePort questionPersistencePort) {
        this.questionnairePersistencePort = questionnairePersistencePort;
        this.exportMapper = exportMapper;
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public ExportDto getbyQuestionnaireUuid(String uuid) {
        var questionnaire = questionnairePersistencePort.findByUuid(uuid);

        RaiseExceptionUtil.raiseIfNull(uuid, questionnaire, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE);

        var questions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);

        var name = generateName(questionnaire);

        return exportMapper.toDto(questionnaire, questions, name);
    }

}
