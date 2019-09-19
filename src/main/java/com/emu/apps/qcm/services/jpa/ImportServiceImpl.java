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
import com.emu.apps.qcm.web.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.mappers.FileQuestionMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Map;

@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private final CategoryService categoryService;

    private final FileQuestionMapper fileQuestionMapper;

    private final QuestionnaireService questionnaireService;

    private final QuestionService questionService;

    private final QuestionnaireTagService questionnaireTagService;

    private final TagService tagService;

    public ImportServiceImpl(QuestionnaireService questionnaireService, TagService tagService, CategoryService categoryService, QuestionService questionService, FileQuestionMapper fileQuestionMapper, QuestionnaireTagService questionnaireTagService) {
        this.questionnaireService = questionnaireService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.fileQuestionMapper = fileQuestionMapper;
        this.questionnaireTagService = questionnaireTagService;
    }



    @Override
    public void createQuestionnaires(String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        Map<String, Questionnaire> categorieQuestionnaireMap = Maps.newHashMap();
        Map<String, Long> tagsCounterMap = Maps.newHashMap();

        Category category = categoryService.findOrCreateByLibelle("Java");

        for (FileQuestionDto fileQuestionDto : fileQuestionDtos) {

            if (StringUtils.isNotEmpty(fileQuestionDto.getCategorie())) {

                Tag categorie = tagService.findOrCreateByLibelle(fileQuestionDto.getCategorie(), principal);

                Long aLong = tagsCounterMap.containsKey(categorie.getLibelle()) ? tagsCounterMap.get(categorie.getLibelle()) : Long.valueOf(0);
                tagsCounterMap.put(categorie.getLibelle(), ++aLong);

                Question question = fileQuestionMapper.dtoToModel(fileQuestionDto);
                question.setType(Type.FREE_TEXT);

                question.setStatus(Status.DRAFT);
                Response response = new Response();
                response.setResponse(fileQuestionDto.getResponse());
                question.setResponses(Lists.newArrayList(response));

                // new questionnaire by tag
                Questionnaire questionnaire = categorieQuestionnaireMap.get(categorie.getLibelle());
                if (questionnaire == null) {
                    questionnaire = new Questionnaire(name + "-" + fileQuestionDto.getCategorie());
                    questionnaire.setDescription(questionnaire.getTitle());
                    questionnaire.setCategory(category);
                    questionnaire.setStatus(Status.DRAFT);
                    questionnaire = questionnaireService.saveQuestionnaire(questionnaire);
                    categorieQuestionnaireMap.put(categorie.getLibelle(), questionnaire);
                }

                question = questionService.saveQuestion(question);

                Long position = tagsCounterMap.get(categorie.getLibelle());

                questionnaireService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, position));

                questionService.saveQuestionTag(new QuestionTag(question, categorie));
                questionnaireTagService.saveQuestionnaireTag(new QuestionnaireTagBuilder().setQuestionnaire(questionnaire).setTag(categorie).build());

            }
        }
    }

}
