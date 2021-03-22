package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.dtos.FileQuestionDto;
import com.emu.apps.qcm.domain.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.domain.models.Status;
import com.emu.apps.qcm.domain.models.imports.ImportStatus;
import com.emu.apps.qcm.domain.models.question.TypeQuestion;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.models.question.Response;
import com.emu.apps.qcm.domain.models.upload.TypeUpload;
import com.emu.apps.qcm.domain.models.upload.Upload;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.question.Question;
import com.emu.apps.qcm.domain.models.question.QuestionTag;
import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.models.upload.UploadId;
import com.emu.apps.qcm.domain.repositories.CategoryRepository;
import com.emu.apps.qcm.domain.repositories.QuestionRepository;
import com.emu.apps.qcm.domain.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.domain.repositories.TagRepository;
import com.emu.apps.qcm.domain.repositories.UploadRepository;
import com.emu.apps.shared.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        if (TypeUpload.EXPORT_JSON.name().equals(upload.getType())) {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .configure(SerializationFeature.INDENT_OUTPUT, true);

            ExportDto exportDataDto = mapper.readValue(new ByteArrayInputStream(upload.getData()), ExportDto.class);
            ImportStatus importStatus = importQuestionnaire(upload.getFileName(), exportDataDto, principal);
            upload.setStatus(importStatus.name());
        } else {
            final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(upload.getData()), FileQuestionDto[].class);
            ImportStatus importStatus = importQuestionnaire(upload.getFileName(), fileQuestionDtos, principal);
            upload.setStatus(importStatus.name());
        }

        return uploadRepository.saveUpload(upload);

    }

    private Question mapToQuestionDto(QuestionExportDto questionExportDto, Category categoryDto) {

        var questionDto = new Question();

        questionDto.setQuestionText(questionExportDto.getQuestionText());
        questionDto.setType(questionExportDto.getType());
        questionDto.setStatus(questionExportDto.getStatus());

        questionDto.setCategory(categoryDto);

        List <Response> responseDtos = new ArrayList <>();
        for (var response : questionExportDto.getResponses()) {

            Response responseDto = new Response();
            responseDto.setResponseText(response.getResponseText());
            responseDto.setGood(response.getGood());
            responseDto.setGood(response.getGood());

            responseDtos.add(responseDto);
        }

        questionDto.setResponses(responseDtos);

        Set <QuestionTag> questionTagDtos = new HashSet <>();

        for (var qtag : questionExportDto.getQuestionTags()) {
            QuestionTag questionTagDto = new QuestionTag();
            questionTagDto.setLibelle(qtag.getLibelle());
            questionTagDtos.add(questionTagDto);
        }
        questionDto.setQuestionTags(questionTagDtos);
        return questionDto;

    }


    private Question mapToQuestionDto(FileQuestionDto fileQuestionDto, Category categoryDto) {

        var questionDto = new Question();

        questionDto.setQuestionText(fileQuestionDto.getQuestion());
        questionDto.setType(TypeQuestion.FREE_TEXT.name());
        questionDto.setStatus(Status.DRAFT.name());

        questionDto.setCategory(categoryDto);

        Response responseDto = new Response();
        responseDto.setResponseText(fileQuestionDto.getResponse());
        questionDto.setResponses(Arrays.asList(responseDto));

        QuestionTag questionTagDto = new QuestionTag();
        questionTagDto.setLibelle(fileQuestionDto.getCategorie());

        questionDto.setQuestionTags(new HashSet <>(Arrays.asList(questionTagDto)));

        return questionDto;

    }


    @Transactional
    public ImportStatus importQuestionnaire(String name, ExportDto exportDataDto, PrincipalId principal) {

        Questionnaire questionnaireDto = new Questionnaire();

        // questionnaire
        questionnaireDto.setTitle(exportDataDto.getQuestionnaire().getTitle());
        questionnaireDto.setStatus(exportDataDto.getQuestionnaire().getStatus());
        if (StringUtils.isNotBlank(exportDataDto.getQuestionnaire().getUuid())) {
            questionnaireDto.setUuid(exportDataDto.getQuestionnaire().getUuid());
            questionnaireDto.setVersion(exportDataDto.getQuestionnaire().getVersion());
            questionnaireDto.setDateCreation(exportDataDto.getQuestionnaire().getDateCreation());
            questionnaireDto.setDateModification(exportDataDto.getQuestionnaire().getDateModification());
        }

        // tags
        HashSet <QuestionnaireTag> qtags = new HashSet <>();
        if (Objects.nonNull(exportDataDto.getQuestionnaire().getQuestionnaireTags())) {
            for (var qtag : exportDataDto.getQuestionnaire().getQuestionnaireTags()) {
                QuestionnaireTag questionnaireTagDto = new QuestionnaireTag();
                questionnaireTagDto.setLibelle(qtag.getLibelle());
                qtags.add(questionnaireTagDto);
            }
        }
        questionnaireDto.setQuestionnaireTags(qtags);

        // categorie
        if (Objects.nonNull(exportDataDto.getQuestionnaire().getCategory())) {
            Category category = new Category();
            category.setLibelle(exportDataDto.getQuestionnaire().getCategory().getLibelle());
            category.setType(TYPE_QUESTIONNAIRE);
            category.setUserId(principal.toUUID());
            category = categoryRepository.saveCategory(category, principal);
            questionnaireDto.setCategory(category);
        }

        Questionnaire questionnaire = questionnaireRepository.saveQuestionnaire(questionnaireDto, principal);

        //questions
        List <Question> questions = exportDataDto.getQuestions()
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

        questionnaireRepository.addQuestions(new QuestionnaireId(questionnaire.getUuid()), questionDtos, principal);

        return ImportStatus.DONE;
    }


    @Transactional
    public ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, PrincipalId principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                Questionnaire questionnaireDto = new Questionnaire();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(Status.DRAFT.name());

                final var tagQuestionnaire = tagRepository.findOrCreateByLibelle(IMPORT, principal);

                QuestionnaireTag questionnaireTagDto = new QuestionnaireTag();
                questionnaireTagDto.setUuid(tagQuestionnaire.getUuid());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(Arrays.asList(questionnaireTagDto)));
                Questionnaire questionnaire = questionnaireRepository.saveQuestionnaire(questionnaireDto, principal);

                Category category = new Category();
                category.setLibelle(IMPORT);
                category.setType(TYPE_QUESTION);
                category.setUserId(principal.toUUID());

                final Category categoryDto = categoryRepository.saveCategory(category, principal);

                List <Question> questions = Arrays
                        .stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> StringUtils.isNotEmpty(fileQuestionDto.getCategorie()))
                        .map(fileQuestionDto -> mapToQuestionDto(fileQuestionDto, categoryDto))
                        .collect(Collectors.toList());

                Collection <Question> questionDtos = questionRepository.saveQuestions(questions, principal);

                questionnaireRepository.addQuestions(new QuestionnaireId(questionnaire.getUuid()), questionDtos, principal);

            } catch (TechnicalException e) {

                return ImportStatus.REJECTED;
            }

        }

        return ImportStatus.DONE;
    }

}
