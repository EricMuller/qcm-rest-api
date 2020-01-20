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

package com.emu.apps.qcm.web.mappers;

import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SuggestMapper {


    @Mapping(target = "fieldName", constant = "TagId")
    SuggestDto modelToSuggestDto(Tag tag);

    Iterable<SuggestDto> modelsToSugestDtos(Iterable<Tag> tags);

    @Mapping(source = "question.title", target = "libelle")
    @Mapping(target = "fieldName", constant = "questionnaireId")
    SuggestDto modelToSuggestDto(QuestionnaireProjection question);

    Iterable<SuggestDto> modelsToSuggestDtos(Iterable<QuestionnaireProjection> questionnaireProjections);
}
