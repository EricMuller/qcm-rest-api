package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.repositories.ExportRepository;
import com.emu.apps.qcm.infra.persistence.ExportPersistencePort;
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

    private final ExportPersistencePort exportPersistencePort;

    public ExportRepositoryAdapter(ExportPersistencePort exportRepository) {
        this.exportPersistencePort = exportRepository;
    }

    @Override
    public ExportDto getbyQuestionnaireUuid(String uuid) {

        return exportPersistencePort.getExportbyQuestionnaireUuid(uuid);
    }

}
