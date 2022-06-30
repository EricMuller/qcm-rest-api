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
import com.emu.apps.qcm.infra.persistence.TagQuestionnairePersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.TagQuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.TagQuestionnaireEntityMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.TagQuestionnaireRepository;
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
public class TagQuestionnairePersistenceAdapter implements TagQuestionnairePersistencePort {

    private final TagQuestionnaireRepository tagQuestionnaireRepository;

    private final TagQuestionnaireEntityMapper tagMapper;

    @Autowired
    public TagQuestionnairePersistenceAdapter(TagQuestionnaireRepository tagQuestionnaireRepository, TagQuestionnaireEntityMapper tagMapper) {
        this.tagQuestionnaireRepository = tagQuestionnaireRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag save(Tag tag) {
        var tagQuestionEntity = tagMapper.modelToEntity(tag);
        return tagMapper.entityToModel(tagQuestionnaireRepository.save(tagQuestionEntity));
    }

    @Override
    public Tag findById(Long id) {
        return tagMapper.entityToModel(tagQuestionnaireRepository.findById(id).orElse(null));
    }

    public Tag findByUuid(String id) {
        return tagMapper.entityToModel(tagQuestionnaireRepository.findByUuid(UUID.fromString(id)).orElse(null));
    }

    @Override
    public Tag findOrCreateByLibelle(String libelle, String principal) {
        var tagEntity = tagQuestionnaireRepository.findByLibelle(libelle, principal);
        if (isNull(tagEntity)) {
            tagEntity = tagQuestionnaireRepository.save(new TagQuestionnaireEntity(libelle));
        }
        return tagMapper.entityToModel(tagEntity);
    }

    @Override
    public Iterable <Tag> findAll() {
        return tagMapper.entitiesToModels(tagQuestionnaireRepository.findAll());
    }

    @Override
    public Page <Tag> findAllByPage(Optional <String> firstLetter, Pageable pageable, String principal) {

        var tagSpecificationBuilder = new TagQuestionnaireEntity.SpecificationBuilder()
                .setPrincipal(principal)
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);

        return tagMapper.pageToModel(tagQuestionnaireRepository.findAll(tagSpecificationBuilder.build(), pageable));
    }

}
