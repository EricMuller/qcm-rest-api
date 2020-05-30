package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.web.dtos.UploadDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface UploadService {
    UploadDto uploadFile(String fileType,
                         MultipartFile multipartFile,
                         Boolean async,
                         Principal principal) throws IOException;

    Iterable <UploadDto> getUploads(Pageable pageable, Principal principal);

    void deleteUploadById(long id);

    UploadDto getUploadById(long id);
}
