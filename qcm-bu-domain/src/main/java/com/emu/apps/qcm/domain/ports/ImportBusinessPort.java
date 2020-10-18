package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.dtos.FileQuestionDto;
import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.api.models.Upload;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.ImportStatus;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface ImportBusinessPort {
    Upload importFile(String uploadUuid, String principal) throws IOException;

    @Transactional
    ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, String principal);

    ImportStatus importQuestionnaire(String name, ExportDto exportDataDto, String principal) ;
}
