package com.emu.apps.qcm.services.jpa;


import com.emu.apps.qcm.services.*;
import com.emu.apps.qcm.services.jpa.entity.Status;
import com.emu.apps.qcm.services.jpa.entity.category.Category;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.questions.Response;
import com.emu.apps.qcm.services.jpa.entity.questions.Type;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTagBuilder;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.rest.mappers.FileQuestionMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Map;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FileQuestionMapper fileQuestionMapper;

    @Autowired
    private QuestionnaireTagService questionnaireTagService;

    @Override
    public void createQuestionnaires(String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        Map<String, Questionnaire> questionnaireCacheMap = Maps.newHashMap();
        Map<String, Long> tagsCounterMap = Maps.newHashMap();

        // Long maxPos = questionnaireService.getMaxPosition();

        //Tag tag = tagService.findOrCreateByLibelle("import");

        Category category = categoryService.findOrCreateByLibelle("Java");

        for (FileQuestionDto fileQuestionDto : fileQuestionDtos) {

            if (StringUtils.isNotEmpty(fileQuestionDto.getCategorie())) {

                // Category category = categoryService.findOrCreateByLibelle(fileQuestionDto.getCategorie());

                Tag tag = tagService.findOrCreateByLibelle(fileQuestionDto.getCategorie(), principal);

                Long aLong = tagsCounterMap.containsKey(tag.getLibelle()) ? tagsCounterMap.get(tag.getLibelle()) : Long.valueOf(0);
                tagsCounterMap.put(tag.getLibelle(), ++aLong);

                Question question = fileQuestionMapper.dtoToModel(fileQuestionDto);
                question.setType(Type.FREE_TEXT);
                // question.setPosition(categoryCounterMap.get(category.getLibelle()));
                question.setStatus(Status.DRAFT);
                Response response = new Response();
                response.setResponse(fileQuestionDto.getResponse());
                question.setResponses(Lists.newArrayList(response));

                // new questionnaire by tag
                Questionnaire questionnaire = questionnaireCacheMap.get(tag.getLibelle());
                if (questionnaire == null) {
                    questionnaire = new Questionnaire(name + "-" + fileQuestionDto.getCategorie());
                    questionnaire.setDescription(questionnaire.getTitle());
                    questionnaire.setCategory(category);
                    questionnaire.setStatus(Status.DRAFT);
                    //questionnaire.setPosition(++maxPos);
                    questionnaire = questionnaireService.saveQuestionnaire(questionnaire);

                    questionnaireCacheMap.put(tag.getLibelle(), questionnaire);
                }

                // question.setPosition(0L);
                question = questionService.saveQuestion(question);

                //question.setQuestionnaire(questionnaire);
                Long position = tagsCounterMap.get(tag.getLibelle());

                questionnaireService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, position));

                questionService.saveQuestionTag(new QuestionTag(question, tag));
                questionnaireTagService.saveQuestionnaireTag(new QuestionnaireTagBuilder().setQuestionnaire(questionnaire).setTag(tag).createQuestionnaireTag());

            }
        }
    }

}
