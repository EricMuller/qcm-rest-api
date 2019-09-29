package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.question.QuestionTagsDto;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + "/questions")
public interface QuestionRestApi {
    @GetMapping(produces = "application/json")
    @Timer

    Iterable<QuestionTagsDto> getQuestions(@RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                           @RequestParam(value = "questionnaire_id", required = false) Long[] questionnaireIds,
                                           Pageable pageable, Principal principal)  ;
    @GetMapping(value = "/{id}")
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTION, key = "#id")
    QuestionDto getQuestionById(@PathVariable("id") long id);

    @PutMapping
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    QuestionDto updateQuestion(@RequestBody @Valid QuestionDto questionDto, Principal principal);

    @PostMapping
    @ResponseBody
    QuestionDto saveQuestion(@RequestBody QuestionDto questionDto, Principal principal);

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTION, condition = "#questionDto != null", key = "#questionDto.id")
    ResponseEntity<Question> deleteQuestionnaireById(@PathVariable("id") long id);
}
