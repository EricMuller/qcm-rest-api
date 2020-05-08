package com.emu.apps.qcm.services.jpa;


import com.emu.apps.qcm.services.*;
import com.emu.apps.qcm.services.entity.Status;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.questions.Type;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.upload.ImportStatus;
import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.services.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.web.dtos.FileQuestionDto;
import com.emu.apps.shared.security.PrincipalUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.emu.apps.qcm.services.entity.category.Type.QUESTION;


@Service
@Transactional
@Log4j2
public class ImportServiceImpl implements ImportService {

    public static final String IMPORT = "import";

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

        try {
            if (fileQuestionDtos.length > 0) {

                Questionnaire newQuestionnaire = new Questionnaire();
                newQuestionnaire.setTitle(IMPORT);
                newQuestionnaire.setStatus(Status.DRAFT);

                final Questionnaire questionnaire = questionnaireService.saveQuestionnaire(newQuestionnaire);

                var tagQuestionnaire = tagService.findOrCreateByLibelle(IMPORT, principal);
                questionnaireTagService.saveQuestionnaireTag(
                        new QuestionnaireTagBuilder()
                                .setQuestionnaire(questionnaire)
                                .setTag(tagQuestionnaire)
                                .build());

                var questionCategory = categoryService.findOrCreateByLibelle(PrincipalUtils.getEmail(principal), QUESTION, IMPORT);
                AtomicLong position  = new AtomicLong();

                Arrays.stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> StringUtils.isNotEmpty(fileQuestionDto.getCategorie()))
                        .forEach(fileQuestionDto -> {
                            var question = new Question();
                            question.setId(fileQuestionDto.getId());
                            question.setQuestion(fileQuestionDto.getQuestion());
                            question.setType(Type.FREE_TEXT);
                            question.setStatus(Status.DRAFT);
                            question.setCategory(questionCategory);

                            Response response = new Response();
                            response.setResponse(fileQuestionDto.getResponse());
                            question.setResponses(Arrays.asList(response));

                            question = questionService.saveQuestion(question);

                            var tag = tagService.findOrCreateByLibelle(fileQuestionDto.getCategorie(), principal);
                            questionService.saveQuestionTag(new QuestionTag(question, tag));

                            questionnaireService.saveQuestionnaireQuestion(new QuestionnaireQuestion(questionnaire, question, position.incrementAndGet()));
                        });

                uploadStatus = ImportStatus.DONE;
            } else {

                uploadStatus = ImportStatus.REJECTED;
            }

        } catch (Exception e) {
            LOGGER.error(e);
            uploadStatus = ImportStatus.REJECTED;
        }

        return uploadStatus;
    }

}
