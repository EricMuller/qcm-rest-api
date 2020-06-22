package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.models.UploadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UploadPersistencePort {

    UploadDto findByUuid(String uuid);

    void deleteByUuid(String uuid);

    UploadDto saveUpload(UploadDto uploadDto);

    Page <UploadDto> findAllByPage(Pageable pageable, String principal);
}
