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

package com.emu.apps.qcm.infra.persistence.mappers.exports;


import com.emu.apps.qcm.domain.dtos.export.v1.CategoryExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionTagExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionnaireExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionnaireTagExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.ResponseExportDto;
import com.emu.apps.qcm.domain.models.question.Question;
import com.emu.apps.qcm.domain.models.question.Response;
import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExportMapper {

    ExportDto modelToExportDto(Questionnaire questionnaire, Iterable <QuestionnaireQuestion> questions, String name);

    QuestionnaireExportDto modelToQuestionnaireExportDto(Questionnaire questionnaire);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionnaireTagExportDto entityToQuestionnaireTagExportDto(QuestionnaireTagEntity questionnaireTagEntity);

    CategoryExportDto entityToCategoryExportDto(CategoryEntity categoryEntity);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionTagExportDto entityToQuestionnaireTagExportDto(QuestionTagEntity questionTagEntity);

    ResponseExportDto entityToResponseExportDto(Response response);

    QuestionExportDto entityToQuestionExportDto(Question question);

    @Mapping(source = "question", target = "questionText")
    QuestionExportDto entityToQuestionExportDto(QuestionnaireQuestion questionnaireQuestion);

    default List <QuestionExportDto> entitiesToQuestionExportDtos(Iterable <QuestionnaireQuestion> questionnaireQuestionEntities) {
        return StreamSupport.stream(questionnaireQuestionEntities.spliterator(), false)
                .map(this::entityToQuestionExportDto)
                .collect(Collectors.toList());
    }

}
