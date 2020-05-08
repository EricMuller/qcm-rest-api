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

package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Profile("webmvc")

@RequestMapping(value = QcmApi.API_V1 + "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public interface QuestionRestApi {
    @GetMapping()
    @Timer
    Iterable<QuestionTagsDto> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                           @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                           Pageable pageable, Principal principal);
    @GetMapping(value = "/{id}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#id")
    @ResponseBody
    QuestionDto getQuestionById(@PathVariable("id") long id);

    @PutMapping(produces = "application/json")
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    @ResponseBody
    QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal);

    @PostMapping(produces = "application/json")
    @ResponseBody
    QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal);

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    ResponseEntity<Question> deleteQuestionById(@PathVariable("id") long id);

}
