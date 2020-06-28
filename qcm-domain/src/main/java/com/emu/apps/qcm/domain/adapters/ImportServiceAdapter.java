package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.ImportServicePort;
import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.dtos.FileQuestionDto;
import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;
import com.emu.apps.qcm.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.Status;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.TypeQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.ImportStatus;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.TypeUpload;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;
import com.emu.apps.qcm.infrastructure.ports.CategoryPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.TagPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.UploadPersistencePort;
import com.emu.apps.qcm.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type.QUESTIONNAIRE;

@Service
@Slf4j
@Transactional
public class ImportServiceAdapter implements ImportServicePort {

    public static final String IMPORT = "import";

    private final TagPersistencePort tagPersistencePort;

    private final UploadPersistencePort uploadPersistencePort;

    private final QuestionnaireServicePort questionnaireService;

    private final QuestionServicePort questionService;

    private final CategoryPersistencePort categoryPersistencePort;

    public ImportServiceAdapter(TagPersistencePort tagPersistencePort, UploadPersistencePort uploadPersistencePort
            , QuestionnaireServicePort questionnaireService, QuestionServicePort questionService, CategoryPersistencePort categoryPersistencePort) {
        this.tagPersistencePort = tagPersistencePort;
        this.uploadPersistencePort = uploadPersistencePort;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public UploadDto importFile(String uploadUuid, String principal) throws IOException {

        var uploadDto = uploadPersistencePort.findByUuid(uploadUuid);

        RaiseExceptionUtil.raiseIfNull(uploadUuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        // todo: strategy
        if (TypeUpload.EXPORT_JSON.name().equals(uploadDto.getType())) {
            ExportDataDto exportDataDto = new ObjectMapper().readValue(new ByteArrayInputStream(uploadDto.getData()), ExportDataDto.class);
            ImportStatus importStatus = importQuestionnaire(uploadDto.getFileName(), exportDataDto, principal);
            uploadDto.setStatus(importStatus.name());
        } else {
            final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(uploadDto.getData()), FileQuestionDto[].class);
            ImportStatus importStatus = importQuestionnaire(uploadDto.getFileName(), fileQuestionDtos, principal);
            uploadDto.setStatus(importStatus.name());
        }


        return uploadPersistencePort.saveUpload(uploadDto);

    }

    private QuestionDto mapToQuestionDto(QuestionExportDto questionExportDto, CategoryDto categoryDto) {

        var questionDto = new QuestionDto();

        questionDto.setQuestion(questionExportDto.getQuestion());
        questionDto.setType(questionExportDto.getType());
        questionDto.setStatus(questionExportDto.getStatus());

        questionDto.setCategory(categoryDto);

        List <ResponseDto> responseDtos = new ArrayList <>();
        for (var response : questionExportDto.getResponses()) {

            ResponseDto responseDto = new ResponseDto();
            responseDto.setResponse(response.getResponse());
            responseDto.setGood(response.getGood());
            responseDto.setGood(response.getGood());

            responseDtos.add(responseDto);
        }

        questionDto.setResponses(responseDtos);

        Set <QuestionTagDto> questionTagDtos = new HashSet <>();

        for (var qtag : questionExportDto.getQuestionTags()) {
            QuestionTagDto questionTagDto = new QuestionTagDto();
            questionTagDto.setLibelle(qtag.getLibelle());
            questionTagDtos.add(questionTagDto);
        }
        questionDto.setQuestionTags(questionTagDtos);
        return questionDto;

    }


    private QuestionDto mapToQuestionDto(FileQuestionDto fileQuestionDto, CategoryDto categoryDto) {

        var questionDto = new QuestionDto();

        questionDto.setQuestion(fileQuestionDto.getQuestion());
        questionDto.setType(TypeQuestion.FREE_TEXT.name());
        questionDto.setStatus(Status.DRAFT.name());

        questionDto.setCategory(categoryDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponse(fileQuestionDto.getResponse());
        questionDto.setResponses(Arrays.asList(responseDto));

        QuestionTagDto questionTagDto = new QuestionTagDto();
        questionTagDto.setLibelle(fileQuestionDto.getCategorie());

        questionDto.setQuestionTags(new HashSet <>(Arrays.asList(questionTagDto)));

        return questionDto;

    }

    @Override
    @Transactional
    public ImportStatus importQuestionnaire(String name, ExportDataDto exportDataDto, String principal) {

        QuestionnaireDto questionnaireDto = new QuestionnaireDto();

        // questionnaire
        questionnaireDto.setTitle(exportDataDto.getQuestionnaire().getTitle());
        questionnaireDto.setStatus(exportDataDto.getQuestionnaire().getStatus());

        // tags
        HashSet <QuestionnaireTagDto> qtags = new HashSet();
        if (Objects.nonNull(exportDataDto.getQuestionnaire().getQuestionnaireTags())) {
            for (var qtag : exportDataDto.getQuestionnaire().getQuestionnaireTags()) {
                QuestionnaireTagDto questionnaireTagDto = new QuestionnaireTagDto();
                questionnaireTagDto.setLibelle(qtag.getLibelle());
                qtags.add(questionnaireTagDto);
            }
        }
        questionnaireDto.setQuestionnaireTags(qtags);

        // categorie
        if (Objects.nonNull(exportDataDto.getQuestionnaire().getCategory())) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setLibelle(exportDataDto.getQuestionnaire().getCategory().getLibelle());
            categoryDto.setType(QUESTIONNAIRE.name());
            categoryDto.setUserId(principal);
            categoryDto = categoryPersistencePort.saveCategory(categoryDto);
            questionnaireDto.setCategory(categoryDto);
        }

        QuestionnaireDto questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

        //questions
        List <QuestionDto> questions = exportDataDto.getQuestions()
                .stream()
                .map(questionExportDto ->
                {
                    CategoryDto categoryDto = null;
                    if (Objects.nonNull(questionExportDto.getCategory())) {
                        categoryDto = new CategoryDto();
                        categoryDto.setLibelle(questionExportDto.getCategory().getLibelle());
                        categoryDto.setType(QUESTION.name());
                        categoryDto.setUserId(principal);
                        categoryDto = categoryPersistencePort.saveCategory(categoryDto);
                    }

                    return mapToQuestionDto(questionExportDto, categoryDto);
                })
                .collect(Collectors.toList());

        Collection questionDtos = questionService.saveQuestions(questions, principal);

        questionnaireService.addQuestions(questionnaire.getUuid(), questionDtos);

        return ImportStatus.DONE;
    }

    @Override
    @Transactional
    public ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, String principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                QuestionnaireDto questionnaireDto = new QuestionnaireDto();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(Status.DRAFT.name());

                final var tagQuestionnaire = tagPersistencePort.findOrCreateByLibelle(IMPORT, principal);

                QuestionnaireTagDto questionnaireTagDto = new QuestionnaireTagDto();
                questionnaireTagDto.setUuid(tagQuestionnaire.getUuid());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(Arrays.asList(questionnaireTagDto)));
                QuestionnaireDto questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

                CategoryDto importCategoryQuestionDto = new CategoryDto();
                importCategoryQuestionDto.setLibelle(IMPORT);
                importCategoryQuestionDto.setType(QUESTION.name());
                importCategoryQuestionDto.setUserId(principal);

                final CategoryDto categoryDto = categoryPersistencePort.saveCategory(importCategoryQuestionDto);

                List <QuestionDto> questions = Arrays
                        .stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> StringUtils.isNotEmpty(fileQuestionDto.getCategorie()))
                        .map(fileQuestionDto -> mapToQuestionDto(fileQuestionDto, categoryDto))
                        .collect(Collectors.toList());

                Collection questionDtos = questionService.saveQuestions(questions, principal);

                questionnaireService.addQuestions(questionnaire.getUuid(), questionDtos);

            } catch (TechnicalException e) {

                return ImportStatus.REJECTED;
            }

        }

        return ImportStatus.DONE;

    }

}
