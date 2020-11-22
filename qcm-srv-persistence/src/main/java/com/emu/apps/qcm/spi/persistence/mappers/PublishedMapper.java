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

package com.emu.apps.qcm.spi.persistence.mappers;

import com.emu.apps.qcm.domain.dtos.published.PublishedQuestionnaireDto;
import com.emu.apps.qcm.domain.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.Objects;

@Mapper(componentModel = "spring", uses = {UuidMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PublishedMapper {

    default String categoryToString(CategoryEntity category) {
        return Objects.nonNull(category) ? category.getLibelle() : null;
    }

    default String questionnaireTagToString(QuestionnaireTagEntity questionnaireTag) {
        return Objects.nonNull(questionnaireTag.getTag()) ? questionnaireTag.getTag().getLibelle() : null;
    }

    Iterable <String> questionnaireTagsToString(Iterable <QuestionnaireTagEntity> categories);

    PublishedQuestionnaireDto questionnaireToPublishedQuestionnaireDto(QuestionnaireEntity questionnaire);

    default Page <PublishedQuestionnaireDto> pageQuestionnaireToPublishedQuestionnaireDto(Page <QuestionnaireEntity> page) {
        return page.map(this::questionnaireToPublishedQuestionnaireDto);
    }

    // questions

    @Mapping(source = "questionnaire.uuid", target = "questionnaireUuid")
    @Mapping(source = "question.type", target = "type")
    @Mapping(source = "question.questionText", target = "questionText")
    @Mapping(source = "question.status", target = "status")
    @Mapping(source = "question.category", target = "category")
    @Mapping(source = "question.responses", target = "responses")

    @Mapping(source = "question.tip", target = "tip")
    @Mapping(source = "position", target = "position")
    PushishedQuestionnaireQuestionDto toPublishedQuestionnaireQuestionDto(QuestionnaireQuestionEntity questionnaireQuestion);

    Iterable <PushishedQuestionnaireQuestionDto> questionnaireQuestionsToPublishedDtos(Iterable <QuestionnaireQuestionEntity> questionnaireQuestions);

}
