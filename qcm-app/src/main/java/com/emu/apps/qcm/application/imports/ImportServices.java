package com.emu.apps.qcm.application.imports;

import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.category.CategoryRepository;


import com.emu.apps.qcm.domain.model.imports.ImportStatus;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.tag.TagRepository;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.upload.UploadId;

import com.emu.apps.qcm.domain.model.upload.UploadRepository;
import com.emu.apps.qcm.infra.reporting.model.Export;
import com.emu.apps.qcm.infra.reporting.model.QuestionExport;
import com.emu.apps.shared.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.domain.model.Status.DRAFT;
import static com.emu.apps.qcm.domain.model.imports.ImportStatus.DONE;
import static com.emu.apps.qcm.domain.model.imports.ImportStatus.REJECTED;
import static com.emu.apps.qcm.domain.model.question.TypeQuestion.FREE_TEXT;
import static com.emu.apps.qcm.domain.model.upload.TypeUpload.EXPORT_JSON;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Service
public class ImportServices {

    public static final String IMPORT = "import";

    public static String TYPE_QUESTION = "QUESTION";

    public static String TYPE_QUESTIONNAIRE = "QUESTIONNAIRE";

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;

    private final UploadRepository uploadRepository;

    private final CategoryRepository categoryRepository;


    public ImportServices(QuestionnaireRepository questionnaireService, QuestionRepository questionService,
                          TagRepository tagRepository, UploadRepository uploadRepository, CategoryRepository categoryRepository) {
        this.questionnaireRepository = questionnaireService;
        this.questionRepository = questionService;
              this.tagRepository = tagRepository;
        this.uploadRepository = uploadRepository;
        this.categoryRepository = categoryRepository;
    }


    public Upload importFile(UploadId uploadId, PrincipalId principal) throws IOException {

        var upload = uploadRepository.getUploadByUuid(uploadId);

        //  use strategy
        if (EXPORT_JSON.name().equals(upload.getType())) {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(WRITE_DATES_AS_TIMESTAMPS)
                    .configure(INDENT_OUTPUT, true);

            Export export = mapper.readValue(new ByteArrayInputStream(upload.getData()), Export.class);
            ImportStatus importStatus = importQuestionnaire(upload.getFileName(), export, principal);
            upload.setStatus(importStatus.name());
        } else {
            final ImportFileQuestion[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(upload.getData()), ImportFileQuestion[].class);
            ImportStatus importStatus = importQuestionnaire(upload.getFileName(), fileQuestionDtos, principal);
            upload.setStatus(importStatus.name());
        }

        return uploadRepository.saveUpload(upload);

    }

    private Question mapToQuestionDto(QuestionExport questionExport, Category categoryDto) {

        var questionDto = new Question();

        questionDto.setQuestionText(questionExport.getQuestionText());
        questionDto.setType(questionExport.getType());
        questionDto.setStatus(questionExport.getStatus());

        questionDto.setCategory(categoryDto);

        List <Response> responseDtos = new ArrayList <>();
        for (var response : questionExport.getResponses()) {

            Response responseDto = new Response();
            responseDto.setResponseText(response.getResponseText());
            responseDto.setGood(response.getGood());
            responseDto.setGood(response.getGood());

            responseDtos.add(responseDto);
        }

        questionDto.setResponses(responseDtos);

        Set <QuestionTag> questionTags = new HashSet <>();

        for (var qtag : questionExport.getQuestionTags()) {
            QuestionTag questionTagDto = new QuestionTag();
            questionTagDto.setLibelle(qtag.getLibelle());
            questionTags.add(questionTagDto);
        }
        questionDto.setQuestionTags(questionTags);
        return questionDto;

    }


    private Question mapToQuestionDto(ImportFileQuestion fileQuestionDto, Category category) {

        var questionDto = new Question();

        questionDto.setQuestionText(fileQuestionDto.getQuestion());
        questionDto.setType(FREE_TEXT.name());
        questionDto.setStatus(DRAFT.name());

        questionDto.setCategory(category);

        Response responseDto = new Response();
        responseDto.setResponseText(fileQuestionDto.getResponse());
        questionDto.setResponses(asList(responseDto));

        QuestionTag questionTagDto = new QuestionTag();
        questionTagDto.setLibelle(fileQuestionDto.getCategorie());

        questionDto.setQuestionTags(new HashSet <>(asList(questionTagDto)));

        return questionDto;

    }


    @Transactional
    public ImportStatus importQuestionnaire(String name, Export export, PrincipalId principal) {

        Questionnaire questionnaire = new Questionnaire();

        // questionnaire
        questionnaire.setTitle(export.getQuestionnaire().getTitle());
        questionnaire.setStatus(export.getQuestionnaire().getStatus());
        if (StringUtils.isNotBlank(export.getQuestionnaire().getUuid())) {
            questionnaire.setUuid(export.getQuestionnaire().getUuid());
            questionnaire.setVersion(export.getQuestionnaire().getVersion());
            questionnaire.setDateCreation(export.getQuestionnaire().getDateCreation());
            questionnaire.setDateModification(export.getQuestionnaire().getDateModification());
        }

        // tags
        HashSet <QuestionnaireTag> qtags = new HashSet <>();
        if (Objects.nonNull(export.getQuestionnaire().getQuestionnaireTags())) {
            for (var qtag : export.getQuestionnaire().getQuestionnaireTags()) {
                QuestionnaireTag questionnaireTagDto = new QuestionnaireTag();
                questionnaireTagDto.setLibelle(qtag.getLibelle());
                qtags.add(questionnaireTagDto);
            }
        }
        questionnaire.setQuestionnaireTags(qtags);

        // categorie
        if (Objects.nonNull(export.getQuestionnaire().getCategory())) {
            Category category = new Category();
            category.setLibelle(export.getQuestionnaire().getCategory().getLibelle());
            category.setType(TYPE_QUESTIONNAIRE);
            category.setUserId(principal.toUUID());
            category = categoryRepository.saveCategory(category, principal);
            questionnaire.setCategory(category);
        }

        Questionnaire newQuestionnaire = questionnaireRepository.saveQuestionnaire(questionnaire, principal);

        //questions
        List <Question> questions = export.getQuestions()
                .stream()
                .map(questionExportDto ->
                {
                    Category categoryDto = null;
                    if (Objects.nonNull(questionExportDto.getCategory())) {
                        categoryDto = new Category();
                        categoryDto.setLibelle(questionExportDto.getCategory().getLibelle());
                        categoryDto.setType(TYPE_QUESTION);
                        categoryDto.setUserId(principal.toUUID());
                        categoryDto = categoryRepository.saveCategory(categoryDto, principal);
                    }

                    return mapToQuestionDto(questionExportDto, categoryDto);
                })
                .collect(Collectors.toList());

        Collection <Question> questionDtos = questionRepository.saveQuestions(questions, principal);

        questionnaireRepository.addQuestions(new QuestionnaireId(newQuestionnaire.getUuid()), questionDtos, principal);

        return DONE;
    }


    @Transactional
    public ImportStatus importQuestionnaire(String name, ImportFileQuestion[] fileQuestionDtos, PrincipalId principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                Questionnaire questionnaireDto = new Questionnaire();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(DRAFT.name());

                final var tagQuestionnaire = tagRepository.findOrCreateByLibelle(IMPORT, principal);

                QuestionnaireTag questionnaireTagDto = new QuestionnaireTag();
                questionnaireTagDto.setUuid(tagQuestionnaire.getUuid());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(asList(questionnaireTagDto)));
                Questionnaire questionnaire = questionnaireRepository.saveQuestionnaire(questionnaireDto, principal);

                Category category = new Category();
                category.setLibelle(IMPORT);
                category.setType(TYPE_QUESTION);
                category.setUserId(principal.toUUID());

                final Category categoryDto = categoryRepository.saveCategory(category, principal);

                List <Question> questions = Arrays
                        .stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> isNotEmpty(fileQuestionDto.getCategorie()))
                        .map(fileQuestionDto -> mapToQuestionDto(fileQuestionDto, categoryDto))
                        .collect(Collectors.toList());

                Collection <Question> questionDtos = questionRepository.saveQuestions(questions, principal);

                questionnaireRepository.addQuestions(new QuestionnaireId(questionnaire.getUuid()), questionDtos, principal);

            } catch (TechnicalException e) {

                return REJECTED;
            }

        }

        return DONE;
    }

}
