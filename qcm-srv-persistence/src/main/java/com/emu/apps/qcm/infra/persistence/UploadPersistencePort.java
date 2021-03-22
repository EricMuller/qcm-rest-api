package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.models.upload.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UploadPersistencePort {

    Optional<Upload> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Upload saveUpload(Upload uploadDto);

    Page <Upload> findAllByPage(Pageable pageable, String principal);
}
