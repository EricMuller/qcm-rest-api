package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.Upload;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository <Upload, Long>, JpaSpecificationExecutor <Upload> {

    @NotNull Page <Upload> findAll(@NotNull Pageable pageable);

    @Override
    @NotNull
    Page <Upload> findAll(Specification <Upload> specification, @NotNull Pageable pageable);

}
