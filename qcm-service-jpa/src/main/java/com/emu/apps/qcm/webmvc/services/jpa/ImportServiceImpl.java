package com.emu.apps.qcm.webmvc.services.jpa;


import com.emu.apps.qcm.webmvc.services.*;
import com.emu.apps.qcm.webmvc.services.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.webmvc.services.jpa.entity.Status;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questions.Response;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questions.Type;
import com.emu.apps.qcm.webmvc.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.web.dtos.FileQuestionDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private final CategoryService categoryService;

    private final QuestionnaireService questionnaireService;

    private final QuestionService questionService;

    private final QuestionnaireTagService questionnaireTagService;

    private final TagService tagService;

    public ImportServiceImpl(QuestionnaireService questionnaireService, TagService tagService, CategoryService categoryService, QuestionService questionService, QuestionnaireTagService questionnaireTagService) {
        this.questionnaireService = questionnaireService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.questionService = questionService;

        this.questionnaireTagService = questionnaireTagService;
    }

    @Override
    public void createQuestionnaires(String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        Map <String, Questionnaire> categorieQuestionnaireMap = new HashMap <>();
        Map <String, Long> tagsCounterMap = new HashMap <>();

        var category = categoryService.findOrCreateByLibelle("Java");

        for (FileQuestionDto fileQuestionDto : fileQuestionDtos) {

            if (StringUtils.isNotEmpty(fileQuestionDto.getCategorie())) {

                var categorie = tagService.findOrCreateByLibelle(fileQuestionDto.getCategorie(), principal);

                var aLong = tagsCounterMap.containsKey(categorie.getLibelle()) ? tagsCounterMap.get(categorie.getLibelle()) : Long.valueOf(0);
                tagsCounterMap.put(categorie.getLibelle(), ++aLong);

                var question = new Question();

                question.setId(fileQuestionDto.getId());
                question.setQuestion(fileQuestionDto.getQuestion());

                question.setType(Type.FREE_TEXT);

                question.setStatus(Status.DRAFT);
                Response response = new Response();
                response.setResponse(fileQuestionDto.getResponse());
                question.setResponses(new ArrayList <>());

                // new questionnaire by tag
                var questionnaire = categorieQuestionnaireMap.get(categorie.getLibelle());
                if (questionnaire == null) {
                    questionnaire = new Questionnaire(name + "-" + fileQuestionDto.getCategorie());
                    questionnaire.setDescription(questionnaire.getTitle());
                    questionnaire.setCategory(category);
                    questionnaire.setStatus(Status.DRAFT);
                    questionnaire = questionnaireService.saveQuestionnaire(questionnaire);
                    categorieQuestionnaireMap.put(categorie.getLibelle(), questionnaire);
                }

                question = questionService.saveQuestion(question);

                var position = tagsCounterMap.get(categorie.getLibelle());

                questionnaireService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, position));

                questionService.saveQuestionTag(new QuestionTag(question, categorie));
                questionnaireTagService.saveQuestionnaireTag(new QuestionnaireTagBuilder().setQuestionnaire(questionnaire).setTag(categorie).build());

            }
        }
    }

}
