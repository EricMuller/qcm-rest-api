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

import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.projections.QuestionResponseProjection;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring"
        , uses = {CategoryEntityMapper.class, QuestionTagEntityMapper.class, ResponseEntityMapper.class, UuidMapper.class, QuestionIdMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireQuestionEntityMapper {

    @Mapping(source = "uuid", target = "id")
    QuestionnaireQuestion questionResponseProjectionToDomain(QuestionResponseProjection questionProjection);

    default Page <QuestionnaireQuestion> pageQuestionResponseProjectionToDto(Page <QuestionResponseProjection> page) {
        return page.map(this::questionResponseProjectionToDomain);
    }

    @Mapping(source = "question.uuid", target = "id")
    @Mapping(source = "question.version", target = "version")
    @Mapping(source = "question.dateCreation", target = "dateCreation")
    @Mapping(source = "question.dateModification", target = "dateModification")
    @Mapping(source = "question.type", target = "type")
    @Mapping(source = "question.questionText", target = "question")
    @Mapping(source = "question.mpttCategory", target = "mpttCategory")
    @Mapping(source = "question.responses", target = "responses")
    @Mapping(source = "question.tags", target = "questionTags")
    @Mapping(source = "question.tip", target = "tip")
    @Mapping(source = "question.status", target = "status")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "points", target = "points")
    @Mapping(source = "question.numeroVersion", target = "numeroVersion")
    QuestionnaireQuestion questionnaireQuestionEntityToDomain(QuestionnaireQuestionEntity questionnaireQuestionEntity);

    default Page <QuestionnaireQuestion> pageQuestionnaireQuestionEntityToDomain(Page <QuestionnaireQuestionEntity> page) {
        return page.map(this::questionnaireQuestionEntityToDomain);
    }

    Iterable <QuestionnaireQuestion> questionnaireQuestionEntityToDomain(Iterable <QuestionnaireQuestionEntity> page);

}
