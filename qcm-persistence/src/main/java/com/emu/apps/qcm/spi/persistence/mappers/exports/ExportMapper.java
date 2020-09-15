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

package com.emu.apps.qcm.spi.persistence.mappers.exports;


import com.emu.apps.qcm.api.dtos.export.v1.*;
import com.emu.apps.qcm.api.models.Questionnaire;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExportMapper {

    ExportDto modelToExportDto(Questionnaire questionnaire, Iterable <QuestionnaireQuestionEntity> questions, String name);

    QuestionnaireExportDto modelToQuestionnaireExportDto(Questionnaire questionnaire);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionnaireTagExportDto entityToQuestionnaireTagExportDto(QuestionnaireTagEntity questionnaireTagEntity);

    CategoryExportDto entityToCategoryExportDto(CategoryEntity categoryEntity);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionTagExportDto entityToQuestionnaireTagExportDto(QuestionTagEntity questionTagEntity);

    ResponseExportDto entityToResponseExportDto(ResponseEntity responseEntity);

    QuestionExportDto entityToQuestionExportDto(QuestionEntity questionEntity);

    List <QuestionExportDto> entitiesToQuestionExportDtos(List <QuestionEntity> questionEntities);

    @Mapping(source = "question.questionText", target = "questionText")
    @Mapping(source = "question.type", target = "type")
    @Mapping(source = "question.status", target = "status")
    @Mapping(source = "question.category", target = "category")
    @Mapping(source = "question.responses", target = "responses")
    @Mapping(source = "question.questionTags", target = "questionTags")
    @Mapping(source = "question.tip", target = "tip")
    @Mapping(source = "points", target = "points")
    QuestionExportDto entityToQuestionExportDto(QuestionnaireQuestionEntity questionnaireQuestionEntity);

    default List <QuestionExportDto> entitiesToQuestionExportDtos(Iterable <QuestionnaireQuestionEntity> questionnaireQuestionEntities) {
        return StreamSupport.stream(questionnaireQuestionEntities.spliterator(), false)
                .map(this::entityToQuestionExportDto)
                .collect(Collectors.toList());
    }

}
