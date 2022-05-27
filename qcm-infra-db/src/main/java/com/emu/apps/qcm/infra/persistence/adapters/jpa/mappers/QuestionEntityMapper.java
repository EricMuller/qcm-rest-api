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

import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreOwner;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTags;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTechniqualData;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.ModelId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.projections.QuestionResponseProjection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        uses = {CategoryEntityMapper.class, QuestionTagEntityMapper.class, ResponseEntityMapper.class, UuidMapper.class, QuestionIdMapper.class, AccountIdMapper.class, AccountEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface QuestionEntityMapper {

    @IgnoreTags
    @IgnoreTechniqualData
    @IgnoreOwner
    QuestionEntity questionToEntity(Question question);

    @ModelId
    Question entityToQuestion(QuestionEntity questionEntity);

    @ModelId
    QuestionWithTagsOnly entityToQuestionWithTagsOnly(QuestionEntity questionEntity);

    @ModelId
    Question questionResponseProjectionToDto(QuestionResponseProjection questionProjection);

    default Page <Question> pageQuestionResponseProjectionToDto(Page <QuestionResponseProjection> page) {
        return page.map(this::questionResponseProjectionToDto);
    }

    default Page <QuestionWithTagsOnly> pageEntityToPageTagDto(Page <QuestionEntity> page) {
        return page.map(this::entityToQuestionWithTagsOnly);
    }


}