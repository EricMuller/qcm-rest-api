package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.dtos.FileQuestionDto;
import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
import com.emu.apps.qcm.api.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.api.models.*;
import com.emu.apps.qcm.domain.ports.ImportBusinessPort;
import com.emu.apps.qcm.domain.ports.QuestionBusinessPort;
import com.emu.apps.qcm.domain.ports.QuestionnaireBusinessPort;
import com.emu.apps.qcm.spi.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.spi.persistence.TagPersistencePort;
import com.emu.apps.qcm.spi.persistence.UploadPersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.Status;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.TypeQuestionEnum;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.ImportStatus;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.TypeUpload;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.spi.persistence.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;

@Service
@Slf4j
@Transactional
public class ImportBusinessAdapter implements ImportBusinessPort {

    public static final String IMPORT = "import";

    private final TagPersistencePort tagPersistencePort;

    private final UploadPersistencePort uploadPersistencePort;

    private final QuestionnaireBusinessPort questionnaireService;

    private final QuestionBusinessPort questionService;

    private final CategoryPersistencePort categoryPersistencePort;

    public ImportBusinessAdapter(TagPersistencePort tagPersistencePort, UploadPersistencePort uploadPersistencePort
            , QuestionnaireBusinessPort questionnaireService, QuestionBusinessPort questionService, CategoryPersistencePort categoryPersistencePort) {
        this.tagPersistencePort = tagPersistencePort;
        this.uploadPersistencePort = uploadPersistencePort;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Upload importFile(String uploadUuid, String principal) throws IOException {

        var uploadDto = uploadPersistencePort.findByUuid(uploadUuid);

        RaiseExceptionUtil.raiseIfNull(uploadUuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        //  use strategy
        if (TypeUpload.EXPORT_JSON.name().equals(uploadDto.getType())) {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .configure(SerializationFeature.INDENT_OUTPUT, true);

            ExportDto exportDataDto = mapper.readValue(new ByteArrayInputStream(uploadDto.getData()), ExportDto.class);
            ImportStatus importStatus = importQuestionnaire(uploadDto.getFileName(), exportDataDto, principal);
            uploadDto.setStatus(importStatus.name());
        } else {
            final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(uploadDto.getData()), FileQuestionDto[].class);
            ImportStatus importStatus = importQuestionnaire(uploadDto.getFileName(), fileQuestionDtos, principal);
            uploadDto.setStatus(importStatus.name());
        }

        return uploadPersistencePort.saveUpload(uploadDto);

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
        questionDto.setType(TypeQuestionEnum.FREE_TEXT.name());
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

    @Override
    @Transactional
    public ImportStatus importQuestionnaire(String name, ExportDto exportDataDto, String principal) {

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
            Category categoryDto = new Category();
            categoryDto.setLibelle(exportDataDto.getQuestionnaire().getCategory().getLibelle());
            categoryDto.setType(QUESTIONNAIRE.name());
            categoryDto.setUserId(principal);
            categoryDto = categoryPersistencePort.saveCategory(categoryDto);
            questionnaireDto.setCategory(categoryDto);
        }

        Questionnaire questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

        //questions
        List <Question> questions = exportDataDto.getQuestions()
                .stream()
                .map(questionExportDto ->
                {
                    Category categoryDto = null;
                    if (Objects.nonNull(questionExportDto.getCategory())) {
                        categoryDto = new Category();
                        categoryDto.setLibelle(questionExportDto.getCategory().getLibelle());
                        categoryDto.setType(QUESTION.name());
                        categoryDto.setUserId(principal);
                        categoryDto = categoryPersistencePort.saveCategory(categoryDto);
                    }

                    return mapToQuestionDto(questionExportDto, categoryDto);
                })
                .collect(Collectors.toList());

        Collection <Question> questionDtos = questionService.saveQuestions(questions, principal);

        questionnaireService.addQuestions(questionnaire.getUuid(), questionDtos, principal);

        return ImportStatus.DONE;
    }

    @Override
    @Transactional
    public ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, String principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                Questionnaire questionnaireDto = new Questionnaire();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(Status.DRAFT.name());

                final var tagQuestionnaire = tagPersistencePort.findOrCreateByLibelle(IMPORT, principal);

                QuestionnaireTag questionnaireTagDto = new QuestionnaireTag();
                questionnaireTagDto.setUuid(tagQuestionnaire.getUuid());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(Arrays.asList(questionnaireTagDto)));
                Questionnaire questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

                Category importCategoryQuestionDto = new Category();
                importCategoryQuestionDto.setLibelle(IMPORT);
                importCategoryQuestionDto.setType(QUESTION.name());
                importCategoryQuestionDto.setUserId(principal);

                final Category categoryDto = categoryPersistencePort.saveCategory(importCategoryQuestionDto);

                List <Question> questions = Arrays
                        .stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> StringUtils.isNotEmpty(fileQuestionDto.getCategorie()))
                        .map(fileQuestionDto -> mapToQuestionDto(fileQuestionDto, categoryDto))
                        .collect(Collectors.toList());

                Collection <Question> questionDtos = questionService.saveQuestions(questions, principal);

                questionnaireService.addQuestions(questionnaire.getUuid(), questionDtos, principal);

            } catch (TechnicalException e) {

                return ImportStatus.REJECTED;
            }

        }

        return ImportStatus.DONE;
    }

}
