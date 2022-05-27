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
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreOwner;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreResponses;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTags;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom.IgnoreTechniqualData;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Mapper(componentModel = "spring",
        uses = {CategoryEntityMapper.class, QuestionTagEntityMapper.class, ResponseEntityMapper.class, UuidMapper.class, QuestionIdMapper.class, AccountIdMapper.class, AccountEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface QuestionEntityUpdateMapper {

    @IgnoreTags
    @IgnoreTechniqualData
    @IgnoreOwner
    @IgnoreResponses
    QuestionEntity questionToEntity(Question question, @MappingTarget QuestionEntity questionEntity);

    ResponseEntity responseToEntity(Response response, @MappingTarget ResponseEntity responseEntity);

    ResponseEntity responseToEntity(Response response);

    /**
     * well fucking wrong idea to keep that technical id...
     *
     * @param responseEntities
     * @param responses
     * @return
     */
    default List <ResponseEntity> responsesToEntities(List <Response> responses, List <ResponseEntity> responseEntities) {
        if (responses == null) {
            responseEntities.clear();
            return emptyList();
        }

        Map <String, ResponseEntity> entitiesMap = responseEntities.stream()
                .collect(Collectors.toMap((r -> r.getUuid().toString()), Function.identity()));

        responseEntities.clear();
        for (Response response : responses) {

            if (entitiesMap.containsKey(response.getUuid())) {
                responseEntities.add(responseToEntity(response, entitiesMap.get(response.getUuid())));
            } else {
                responseEntities.add(responseToEntity(response));
            }
        }

        return responseEntities;
    }


}
