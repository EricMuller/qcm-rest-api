package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.caches.CacheName;
import com.emu.apps.shared.metrics.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 + "/questionnaires")
public interface QuestionnaireRestApi {

    @GetMapping(value = "/{id}", consumes = {"application/json"})
    @ResponseBody
    @Timer
    @Cacheable(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    QuestionnaireDto getQuestionnaireById(@Parameter(description = "Questionnaire Id", required = true) @PathVariable("id") long id);

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = CacheName.Names.QUESTIONNAIRE, key = "#id")
    ResponseEntity<Questionnaire> deleteQuestionnaireById(@PathVariable("id") long id);

    @PutMapping
    @ResponseBody
    @CachePut(cacheNames = CacheName.Names.QUESTIONNAIRE, condition = "#questionnaireDto != null", key = "#questionnaireDto.id")
    @Timer
    QuestionnaireDto updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @PostMapping
    @ResponseBody
    QuestionnaireDto saveQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, Principal principal);

    @GetMapping(value = "/{id:[\\d]+}/questions")
    @ResponseBody
    Page<QuestionDto> getQuestionsByQuestionnaireId(@PathVariable("id") long id, Pageable pageable);

    @ResponseBody
    @GetMapping(produces = "application/json")
    @Timer
    Iterable<QuestionnaireDto> getQuestionnaires(@Parameter(description = "Identifiant Tag(s)", required = true) @RequestParam(value = "tag_id", required = false) Long[] tagIds,
                                                        Pageable pageable, Principal principal);

    @PutMapping(value = "/{id}/questions")
    @ResponseBody
    QuestionDto updateQuestionnaire(@PathVariable("id") long id, @RequestBody QuestionDto questionDto);
}
