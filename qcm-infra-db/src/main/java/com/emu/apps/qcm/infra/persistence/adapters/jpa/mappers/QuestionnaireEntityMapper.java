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

package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers;


import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTags;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTechniqualData;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.ModelId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.Optional;


@Mapper(componentModel = "spring",
        uses = {CategoryEntityMapper.class, QuestionnaireTagEntityMapper.class, UuidMapper.class, QuestionnaireIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireEntityMapper {


    @ModelId
    Questionnaire modelToDto(QuestionnaireEntity questionnaire);

    @IgnoreTags
    @IgnoreTechniqualData
    QuestionnaireEntity dtoToModel(Questionnaire questionnaire);

    @IgnoreTags
    @IgnoreTechniqualData
    QuestionnaireEntity dtoToModel(Questionnaire questionnaire, @MappingTarget QuestionnaireEntity questionnaireEntity);

    default Page <Questionnaire> pageToDto(Page <QuestionnaireEntity> page) {
        return page.map(this::modelToDto);
    }


    @Named("unwrapOptional")
    default <T> T unwrapOptional(Optional <T> optional) {
        return optional.orElse(null);
    }

}
