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

package com.emu.apps.qcm.infra.persistence.adapters.mappers;

import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class,UuidMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionTagEntityMapper {

    @Mapping(source = "tag.uuid", target = "uuid")
    @Mapping(source = "tag.libelle", target = "libelle")

    @Mapping(source = "tag.dateCreation", target = "dateCreation")
    @Mapping(source = "tag.dateModification", target = "dateModification")
    @Mapping(source = "tag.createdBy", target = "createdBy")
    @Mapping(source = "tag.lastModifiedBy", target = "lastModifiedBy")

    QuestionTag entityToModel(QuestionTagEntity questionTagEntity);


    @Mapping(source = "libelle", target = "tag.libelle")
    QuestionTagEntity modelToEntity(QuestionTag questionTag);

    Iterable <QuestionTagEntity> modelsToEntities(Iterable <QuestionTag> questionTags);

}
