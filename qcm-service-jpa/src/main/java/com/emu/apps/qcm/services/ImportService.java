package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.web.dtos.FileQuestionDto;

import java.io.IOException;
import java.security.Principal;

public interface ImportService {

    Upload importUpload(Upload upload, Principal principal) throws IOException;

}
