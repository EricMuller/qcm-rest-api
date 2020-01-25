package com.emu.apps.qcm.services.jpa;


import com.emu.apps.qcm.services.*;
import com.emu.apps.qcm.services.entity.Status;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.questions.Type;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.services.entity.upload.ImportStatus;
import com.emu.apps.qcm.services.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.web.dtos.FileQuestionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private final UploadService uploadService;

    @Autowired
    public ImportServiceImpl(CategoryService categoryService, QuestionnaireService questionnaireService, QuestionService questionService, QuestionnaireTagService questionnaireTagService, TagService tagService, UploadService uploadService) {
        this.categoryService = categoryService;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.questionnaireTagService = questionnaireTagService;
        this.tagService = tagService;
        this.uploadService = uploadService;
    }

    @Override
    public Upload importUpload(Upload upload, Principal principal) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(upload.getData());

        final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(inputStream, FileQuestionDto[].class);

        upload.setStatus(importQuestionnaire(upload.getStatus(), upload.getFileName(), fileQuestionDtos, principal));

        return uploadService.saveUpload(upload);

    }


    public ImportStatus importQuestionnaire(ImportStatus aUploadStatus, String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        var uploadStatus = aUploadStatus;

        if (fileQuestionDtos.length > 0) {

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

            uploadStatus = ImportStatus.DONE;
        } else {

            uploadStatus = ImportStatus.REJECTED;
        }

        return uploadStatus;
    }

}
