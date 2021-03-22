package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.upload.Upload;
import com.emu.apps.qcm.domain.models.upload.UploadId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadRepository {
    Upload uploadFile(String fileType,
                      MultipartFile multipartFile,
                      Boolean async,
                      PrincipalId principal) throws IOException;

    Iterable <Upload> getUploads(Pageable pageable, PrincipalId principal);

    void deleteUploadByUuid(UploadId uploadId);

    Upload getUploadByUuid(UploadId uploadId);

    Upload saveUpload(Upload upload) ;

}
