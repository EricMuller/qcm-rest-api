package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.UploadDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.ImportStatus;
import com.emu.apps.qcm.dtos.FileQuestionDto;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface ImportServicePort {
    UploadDto importFile(String uploadUuid, String principal) throws IOException;

    @Transactional
    ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, String principal);
}
