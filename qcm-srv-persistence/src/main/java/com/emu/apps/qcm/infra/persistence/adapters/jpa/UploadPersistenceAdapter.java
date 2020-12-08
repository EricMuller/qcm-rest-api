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

import com.emu.apps.qcm.domain.models.Upload;
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
import java.util.UUID;

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
    public Upload findByUuid(String id) {
        return uploadMapper.modelToDto(uploadRepository.findByUuid(UUID.fromString(id)).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid) {
        uploadRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    public Upload saveUpload(Upload uploadDto) {

        UploadEntity upload;
        if (Objects.nonNull(uploadDto.getUuid())) {
            upload = uploadRepository.findByUuid(UUID.fromString(uploadDto.getUuid())).orElse(null);
            upload = uploadMapper.dtoToModel(upload, uploadDto);
        } else {
            upload = uploadMapper.dtoToModel(uploadDto);
        }

        return uploadMapper.modelToDto(uploadRepository.save(upload));
    }

    @Override
    @Transactional(readOnly = true)
    public Page <Upload> findAllByPage(Pageable pageable, String principal) {

        var uploadSpecificationBuilder = new UploadEntity.SpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(principal);

        return uploadMapper.pageToPageDto(uploadRepository.findAll(uploadSpecificationBuilder.build(), pageable));
    }

}
