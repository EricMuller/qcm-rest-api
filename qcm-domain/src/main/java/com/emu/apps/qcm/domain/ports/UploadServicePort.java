package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.UploadDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadServicePort {
    UploadDto uploadFile(String fileType,
                         MultipartFile multipartFile,
                         Boolean async,
                         String principal) throws IOException;

    Iterable <UploadDto> getUploads(Pageable pageable, String principal);

    void deleteUploadByUuid(String uuid);

    UploadDto getUploadByUuid(String uuid);
}
