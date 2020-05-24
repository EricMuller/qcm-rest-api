package com.emu.apps.qcm.services;

import com.emu.apps.qcm.domain.TagDOService;
import com.emu.apps.qcm.domain.UploadDOService;
import com.emu.apps.qcm.domain.entity.Status;
import com.emu.apps.qcm.domain.entity.questions.TypeQuestion;
import com.emu.apps.qcm.domain.entity.upload.ImportStatus;
import com.emu.apps.qcm.domain.entity.upload.Upload;
import com.emu.apps.qcm.domain.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.domain.exceptions.TechnicalException;
import com.emu.apps.qcm.web.dtos.*;
import com.emu.apps.qcm.mappers.UploadMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.emu.apps.qcm.domain.entity.category.Type.QUESTION;

@Service
@Slf4j
@Transactional
public class ImportService {

    public static final String IMPORT = "import";

    private final TagDOService tagDOService;

    private final UploadDOService uploadDOService;

    private final UploadMapper uploadMapper;

    private final QuestionnaireService questionnaireService;

    private final QuestionService questionService;

    public ImportService(TagDOService tagDOService, UploadDOService uploadDOService, UploadMapper uploadMapper, QuestionnaireService questionnaireService, QuestionService questionDelegate) {
        this.tagDOService = tagDOService;
        this.uploadDOService = uploadDOService;
        this.uploadMapper = uploadMapper;
        this.questionnaireService = questionnaireService;
        this.questionService = questionDelegate;
    }

    public UploadDto importFile(Long uploadId, Principal principal) throws IOException {

        var optionalUpload = uploadDOService.findById(uploadId);
        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(uploadId));
        }

        return uploadMapper.modelToDto(importUpload(optionalUpload.get(), principal));

    }


    private Upload importUpload(Upload upload, Principal principal) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(upload.getData());

        final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(inputStream, FileQuestionDto[].class);

        upload.setStatus(importQuestionnaire(upload.getStatus(), upload.getFileName(), fileQuestionDtos, principal));

        return uploadDOService.saveUpload(upload);

    }

    private QuestionDto mapToQuestionDto(FileQuestionDto fileQuestionDto, CategoryDto categoryDto) {

        var questionDto = new QuestionDto();
        questionDto.setId(fileQuestionDto.getId());
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

    @Transactional
    public ImportStatus importQuestionnaire(ImportStatus aUploadStatus, String name, FileQuestionDto[] fileQuestionDtos, Principal principal) {

        if (ArrayUtils.isNotEmpty(fileQuestionDtos)) {
            try {
                QuestionnaireDto questionnaireDto = new QuestionnaireDto();

                questionnaireDto.setTitle(IMPORT);
                questionnaireDto.setStatus(Status.DRAFT.name());

                final var tagQuestionnaire = tagDOService.findOrCreateByLibelle(IMPORT, principal);

                QuestionnaireTagDto questionnaireTagDto = new QuestionnaireTagDto();
                questionnaireTagDto.setId(tagQuestionnaire.getId());
                questionnaireDto.setQuestionnaireTags(new HashSet <>(Arrays.asList(questionnaireTagDto)));
                QuestionnaireDto questionnaire = questionnaireService.saveQuestionnaire(questionnaireDto, principal);

                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setLibelle(IMPORT);
                categoryDto.setType(QUESTION.name());
                categoryDto.setUserId(PrincipalUtils.getEmail(principal));

                List <QuestionDto> questions = Arrays
                        .stream(fileQuestionDtos)
                        .filter(fileQuestionDto -> StringUtils.isNotEmpty(fileQuestionDto.getCategorie()))
                        .map(fileQuestionDto -> mapToQuestionDto(fileQuestionDto, categoryDto))
                        .collect(Collectors.toList());

                Collection questionDtos = questionService.saveQuestions(questions, principal);

                questionnaireService.addQuestions(questionnaire.getId(), questionDtos);

            } catch (TechnicalException e) {

                return ImportStatus.REJECTED;
            }

        }

        return ImportStatus.DONE;

    }


}
