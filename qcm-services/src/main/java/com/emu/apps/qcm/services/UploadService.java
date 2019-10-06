package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UploadService {

    Optional<Upload> findById(Long id);

    void deleteById(Long id);

    Upload saveUpload(Upload question);

    Page<Upload> findAllByPage(Specification<Upload> specification, Pageable pageable);
}
