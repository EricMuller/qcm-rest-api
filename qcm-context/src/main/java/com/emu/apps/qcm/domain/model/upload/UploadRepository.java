package com.emu.apps.qcm.domain.model.upload;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadRepository {
    Upload uploadFile(String fileType,
                      MultipartFile multipartFile,
                      Boolean async,
                      PrincipalId principal) throws IOException;

    Page <Upload> getUploads(Pageable pageable, PrincipalId principal);

    void deleteUploadByUuid(UploadId uploadId);

    Upload getUploadByUuid(UploadId uploadId);

    Upload saveUpload(Upload upload);

}
