package com.emu.apps.qcm.spi.persistence;

import com.emu.apps.qcm.api.models.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UploadPersistencePort {

    Upload findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Upload saveUpload(Upload uploadDto);

    Page <Upload> findAllByPage(Pageable pageable, String principal);
}
