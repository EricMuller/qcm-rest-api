package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.UploadService;
import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import com.emu.apps.qcm.services.jpa.repositories.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional()
public class UploadServiceImpl implements UploadService {

    private final UploadRepository uploadRepository;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Upload> findById(Long id) {
        return uploadRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        uploadRepository.deleteById(id);
    }

    @Override
    public Upload saveUpload(Upload upload) {
        return uploadRepository.save(upload);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Upload> findAllByPage(Specification<Upload> specification, Pageable pageable) {
        return uploadRepository.findAll(specification, pageable);
    }

}
