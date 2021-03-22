package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.repositories.ExportRepository;
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
public class ExportRepositoryAdapter implements ExportRepository {

//    private final QuestionnairePersistencePort questionnairePersistencePort;
////
////    private final ExportMapper exportMapper;
//
//    private final QuestionPersistencePort questionPersistencePort;

//    public ExportRepositoryAdapter(QuestionnairePersistencePort questionnairePersistencePort, ExportMapper exportMapper, QuestionPersistencePort questionPersistencePort) {
//        this.questionnairePersistencePort = questionnairePersistencePort;
//        this.exportMapper = exportMapper;
//        this.questionPersistencePort = questionPersistencePort;
//    }

    @Override
    public ExportDto getbyQuestionnaireUuid(String uuid) {
//        var questionnaire = questionnairePersistencePort.findByUuid(uuid)
//                .orElseThrow(() -> new EntityNotFoundException(uuid, MessageSupport.UNKNOWN_UUID_QUESTIONNAIRE));
//
//        var questions = questionPersistencePort.findAllWithTagsAndResponseByQuestionnaireUuid(uuid);
//
//        return exportMapper.modelToExportDto(questionnaire, questions, generateName(questionnaire));

        return new ExportDto();
    }

}
