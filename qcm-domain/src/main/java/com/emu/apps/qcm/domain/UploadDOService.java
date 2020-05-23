package com.emu.apps.qcm.domain;

import com.emu.apps.qcm.domain.entity.upload.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UploadDOService {

    Optional <Upload> findById(Long id);

    void deleteById(Long id);

    Upload saveUpload(Upload upload);

    Page <Upload> findAllByPage(Specification <Upload> specification, Pageable pageable);
}
