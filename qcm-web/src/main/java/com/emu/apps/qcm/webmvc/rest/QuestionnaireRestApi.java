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

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.webmvc.rest.caches.CacheName;
import com.emu.shared.metrics.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RequestMapping(value = QcmApi.API_V1 + "/questionnaires", produces = MediaType.APPLICATION_JSON_VALUE)
public interface QuestionnaireRestApi {

    @GetMapping(value = "/{id}")
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    QuestionnaireDto getQuestionnaireById(@PathVariable("id") long id);

    @DeleteMapping(value = "/{id}")
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    @ResponseBody
    ResponseEntity<Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id);

    @PutMapping()
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    @ResponseBody
    QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @PostMapping()
    @ResponseBody
    QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @GetMapping(value = "/{id:[\\d]+}/questions")
    @ResponseBody
    Page<QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable);


    @GetMapping(produces = "application/json")
    @Timer
    @ResponseBody
    Page<QuestionnaireDto> getQuestionnaires(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                             Pageable pageable, Principal principal);

    @PutMapping(value = "/{id}/questions")
    @ResponseBody
    QuestionDto updateQuestionnaire(@PathVariable("id") long id, @RequestBody QuestionDto questionDto);
}
