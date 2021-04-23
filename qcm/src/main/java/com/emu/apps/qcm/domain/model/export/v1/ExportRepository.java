package com.emu.apps.qcm.domain.model.export.v1;

import com.emu.apps.qcm.domain.model.export.v1.Export;

public interface ExportRepository {
    Export getbyQuestionnaireUuid(String id);
}
