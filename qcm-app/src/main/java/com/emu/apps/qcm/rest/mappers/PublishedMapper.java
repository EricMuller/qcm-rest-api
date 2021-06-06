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

package com.emu.apps.qcm.rest.mappers;

import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.UuidMapper;
import com.emu.apps.qcm.rest.resources.published.PublishedQuestionnaire;
import com.emu.apps.qcm.rest.resources.published.PushishedQuestionnaireQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {UuidMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PublishedMapper {

    default String categoryToString(Category category) {
        return Objects.nonNull(category) ? category.getLibelle() : null;
    }

    default String questionIdToString(QuestionId questionId) {
        return Objects.nonNull(questionId) ? questionId.toUuid() : null;
    }

   default String questionnaireTagToString(QuestionnaireTag questionnaireTag) {
        return questionnaireTag.getLibelle();
    }

    default String questionTagToString(QuestionTag questionTag) {
        return questionTag.getLibelle();
    }

    Set <String> questionTagsToString(Set <QuestionTag> questionTags);

    Set <String> questionnaireTagsToString(Set <QuestionnaireTag> questionnaireTags);

    PublishedQuestionnaire questionnaireToPublishedQuestionnaire(Questionnaire questionnaire);

    default Page <PublishedQuestionnaire> pageQuestionnaireToPublishedQuestionnaires(Page <Questionnaire> page) {
        return page.map(this::questionnaireToPublishedQuestionnaire);
    }

    // questions
    @Mapping(source = "id", target = "questionnaireUuid")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "question", target = "questionText")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "responses", target = "responses")

    @Mapping(source = "tip", target = "tip")
    @Mapping(source = "position", target = "position")
    PushishedQuestionnaireQuestion toPublishedQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion);

    Iterable <PushishedQuestionnaireQuestion> questionnaireQuestionsToPublishedDtos(Iterable <QuestionnaireQuestion> questionnaireQuestions);

}
