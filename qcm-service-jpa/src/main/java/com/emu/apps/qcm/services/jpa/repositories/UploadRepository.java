package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long>, JpaSpecificationExecutor<Upload> {

    Page<Upload> findAll(Pageable pageable);

    @Override
    Page<Upload> findAll(Specification<Upload> specification, Pageable pageable);

}
