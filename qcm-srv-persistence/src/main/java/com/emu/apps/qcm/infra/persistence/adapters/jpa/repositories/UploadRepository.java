package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.upload.UploadEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UploadRepository extends JpaRepository <UploadEntity, Long>, JpaSpecificationExecutor <UploadEntity> {

    @NotNull Page <UploadEntity> findAll(@NotNull Pageable pageable);

    @Override
    @NotNull
    Page <UploadEntity> findAll(Specification <UploadEntity> specification, @NotNull Pageable pageable);

    Optional<UploadEntity> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

}
