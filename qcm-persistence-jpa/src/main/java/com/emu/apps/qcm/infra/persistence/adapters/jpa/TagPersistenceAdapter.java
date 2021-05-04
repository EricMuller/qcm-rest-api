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

import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.infra.persistence.TagPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagRepository;
import com.emu.apps.qcm.infra.persistence.mappers.TagEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class TagPersistenceAdapter implements TagPersistencePort {

    private final TagRepository tagRepository;

    private final TagEntityMapper tagMapper;

    @Autowired
    public TagPersistenceAdapter(TagRepository tagRepository, TagEntityMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag save(Tag tag) {
        TagEntity tagEntity = tagMapper.dtoToModel(tag);
        return tagMapper.modelToDto(tagRepository.save(tagEntity));
    }

    @Override
    public Tag findById(Long id) {
        return tagMapper.modelToDto(tagRepository.findById(id).orElse(null));
    }

    public Tag findByUuid(String id) {
        return tagMapper.modelToDto(tagRepository.findByUuid(UUID.fromString(id)).orElse(null));
    }

    @Override
    public Tag findOrCreateByLibelle(String libelle, String principal) {
        var tagEntity = tagRepository.findByLibelle(libelle, principal);
        if (isNull(tagEntity)) {
            tagEntity = tagRepository.save(new TagEntity(libelle, true));
        }
        return tagMapper.modelToDto(tagEntity);
    }

    @Override
    public Iterable <Tag> findAll() {
        return tagMapper.modelsToDtos(tagRepository.findAll());
    }

    @Override
    public Page <Tag> findAllByPage(Optional <String> firstLetter, Pageable pageable, String principal) {

        var tagSpecificationBuilder = new TagEntity.SpecificationBuilder()
                .setPrincipal(principal)
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);

        return tagMapper.pageToDto(tagRepository.findAll(tagSpecificationBuilder.build(), pageable));
    }

}
