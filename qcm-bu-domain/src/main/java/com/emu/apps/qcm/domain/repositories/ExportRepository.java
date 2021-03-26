package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;

public interface ExportRepository {
    ExportDto getbyQuestionnaireUuid(String id);
}
