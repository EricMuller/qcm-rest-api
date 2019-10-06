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


import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, QuestionnaireTagMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireMapper {

    QuestionnaireDto modelToDto(Questionnaire questionnaire);

    @Mapping(target = "questionnaireQuestions", ignore = true)
    @Mapping(target = "questionnaireTags", ignore = true)
    Questionnaire dtoToModel(QuestionnaireDto questionnaireDto);

    @Mapping(target = "questionnaireQuestions", ignore = true)
    @Mapping(target = "questionnaireTags", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Questionnaire dtoToModel(@MappingTarget Questionnaire questionnaire, QuestionnaireDto questionnaireDto);

    default Page<QuestionnaireDto> pageToDto(Page<Questionnaire> page) {
        return page.map(this::modelToDto);
    }

}
