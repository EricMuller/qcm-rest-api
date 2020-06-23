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

package com.emu.apps.qcm.mappers.exports;


import com.emu.apps.qcm.dtos.export.*;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Response;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.models.QuestionnaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExportMapper {

    ExportDataDto toDto(QuestionnaireDto questionnaire, Iterable <QuestionnaireQuestion> questions, String name);

    QuestionnaireExportDto modelToDto(QuestionnaireDto questionnaire);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionnaireTagExportDto dtoToModel(QuestionnaireTag questionnaireTag);

    CategoryExportDto modelToDto(Category category);

    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionTagExportDto dtoToModel(QuestionTag questionTag);

    ResponseExportDto modelToDto(Response response);

    QuestionExportDto modelToDto(Question question);

    List <QuestionExportDto> modelToDtos(List <Question> questions);

    @Mapping(source = "question.question", target = "question")
    @Mapping(source = "question.type", target = "type")
    @Mapping(source = "question.status", target = "status")
    @Mapping(source = "question.category", target = "category")
    @Mapping(source = "question.responses", target = "responses")
    @Mapping(source = "question.questionTags", target = "questionTags")
    @Mapping(source = "question.tip", target = "tip")
    QuestionExportDto modelToDto(QuestionnaireQuestion questionnaireQuestion);

    default List <QuestionExportDto> modelToDtos(Iterable <QuestionnaireQuestion> value) {
        return StreamSupport.stream(value.spliterator(), false)
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

}
