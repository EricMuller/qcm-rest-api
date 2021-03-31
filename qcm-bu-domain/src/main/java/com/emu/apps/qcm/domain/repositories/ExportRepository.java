package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.export.v1.Export;

public interface ExportRepository {
    Export getbyQuestionnaireUuid(String id);
}
