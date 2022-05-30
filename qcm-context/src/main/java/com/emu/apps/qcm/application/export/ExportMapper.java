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

package com.emu.apps.qcm.application.export;


import com.emu.apps.qcm.application.export.dto.Export;
import com.emu.apps.qcm.application.export.dto.QuestionExport;
import com.emu.apps.qcm.application.export.dto.QuestionnaireExport;
import com.emu.apps.qcm.application.export.dto.ResponseExport;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExportMapper {

    Export modelToExportDto(Questionnaire questionnaire, Iterable <QuestionnaireQuestion> questions, String exportName);

    QuestionnaireExport modelToQuestionnaireExportDto(Questionnaire questionnaire);

    ResponseExport entityToResponseExportDto(Response response);

    QuestionExport entityToQuestionExportDto(Question question);

    @Mapping(source = "question", target = "questionText")
    QuestionExport entityToQuestionExportDto(QuestionnaireQuestion questionnaireQuestion);

    default List <QuestionExport> entitiesToQuestionExportDtos(Iterable <QuestionnaireQuestion> questionnaireQuestionEntities) {
        return StreamSupport.stream(questionnaireQuestionEntities.spliterator(), false)
                .map(this::entityToQuestionExportDto)
                .collect(Collectors.toList());
    }

}
