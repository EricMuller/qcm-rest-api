package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Upload;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadRepository {
    Upload uploadFile(String fileType,
                      MultipartFile multipartFile,
                      Boolean async,
                      String principal) throws IOException;

    Iterable <Upload> getUploads(Pageable pageable, String principal);

    void deleteUploadByUuid(String uuid);

    Upload getUploadByUuid(String uuid);

    Upload saveUpload(Upload upload) ;

}
