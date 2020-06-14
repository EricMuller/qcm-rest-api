package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.dtos.*;
import com.emu.apps.qcm.domain.ports.ImportServicePort;
import com.emu.apps.qcm.domain.ports.QuestionServicePort;
import com.emu.apps.qcm.domain.ports.QuestionnaireServicePort;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.Status;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.TypeQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.ImportStatus;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;
import com.emu.apps.qcm.infrastructure.ports.CategoryPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.TagPersistencePort;
import com.emu.apps.qcm.infrastructure.ports.UploadPersistencePort;
import com.emu.apps.qcm.web.dtos.*;
import com.emu.apps.shared.security.PrincipalUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type.QUESTION;

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
    public UploadDto importFile(String uploadUuid, Principal principal) throws IOException {

        var uploadDto = uploadPersistencePort.findByUuid(uploadUuid);

        EntityExceptionUtil.raiseExceptionIfNull(uploadUuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(uploadDto.getData()), FileQuestionDto[].class);

        ImportStatus importStatus = importQuestionnaire(uploadDto.getFileName(), fileQuestionDtos, principal);

        uploadDto.setStatus(importStatus.name());

        return uploadPersistencePort.saveUpload(uploadDto);

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
    public ImportStatus importQuestionnaire(String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                QuestionnaireDto questionnaireDto = new QuestionnaireDto();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(Status.DRAFT.name());

                final var tagQuestionnaire = tagPersistencePort.findOrCreateByLibelle(IMPORT, PrincipalUtils.getEmail(principal));

                QuestionnaireTagDto questionnaireTagDto = new QuestionnaireTagDto();
                questionnaireTagDto.setUuid(tagQuestionnaire.getUuid().toString());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(Arrays.asList(questionnaireTagDto)));
                QuestionnaireDto questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

                CategoryDto importCategoryQuestionDto = new CategoryDto();
                importCategoryQuestionDto.setLibelle(IMPORT);
                importCategoryQuestionDto.setType(QUESTION.name());
                importCategoryQuestionDto.setUserId(PrincipalUtils.getEmail(principal));

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
