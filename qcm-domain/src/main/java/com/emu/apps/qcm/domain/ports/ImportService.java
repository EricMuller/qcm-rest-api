package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.ImportStatus;
import com.emu.apps.qcm.web.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.dtos.UploadDto;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;

public interface ImportService {
    UploadDto importFile(Long uploadId, Principal principal) throws IOException;

    @Transactional
    ImportStatus importQuestionnaire(ImportStatus aUploadStatus, String name, FileQuestionDto[] fileQuestionDtos, Principal principal);
}
