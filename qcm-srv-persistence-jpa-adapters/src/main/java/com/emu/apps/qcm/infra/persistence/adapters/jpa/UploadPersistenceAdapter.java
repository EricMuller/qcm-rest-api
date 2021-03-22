/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c)  2019 qcm-rest-api
 * Author  Eric Muller
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.models.upload.Upload;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.upload.UploadEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.UploadRepository;
import com.emu.apps.qcm.infra.persistence.UploadPersistencePort;
import com.emu.apps.qcm.infra.persistence.mappers.UploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional
public class UploadPersistenceAdapter implements UploadPersistencePort {

    private final UploadRepository uploadRepository;

    private final UploadMapper uploadMapper;

    @Autowired
    public UploadPersistenceAdapter(UploadRepository uploadRepository, UploadMapper uploadMapper) {
        this.uploadRepository = uploadRepository;
        this.uploadMapper = uploadMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Upload> findByUuid(String uuid) {
        return ofNullable(uploadMapper.modelToDto(uploadRepository.findByUuid(UUID.fromString(uuid)).orElse(null)));
    }

    @Override
    public void deleteByUuid(String uuid) {
        uploadRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    public Upload saveUpload(Upload upload) {

        UploadEntity uploadEntity;
        if (nonNull(upload.getUuid())) {
            uploadEntity = uploadRepository.findByUuid(UUID.fromString(upload.getUuid())).orElse(null);
            uploadEntity = uploadMapper.dtoToModel(uploadEntity, upload);
        } else {
            uploadEntity = uploadMapper.dtoToModel(upload);
        }

        return uploadMapper.modelToDto(uploadRepository.save(uploadEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Upload> findAllByPage(Pageable pageable, String principal) {

        var uploadSpecificationBuilder = new UploadEntity.SpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(principal);

        return uploadMapper.pageToPageDto(uploadRepository.findAll(uploadSpecificationBuilder.build(), pageable));
    }

}
