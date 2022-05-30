package com.emu.apps.qcm.application.importation;

import com.emu.apps.qcm.application.export.dto.Export;
import com.emu.apps.qcm.application.export.dto.QuestionExport;
import com.emu.apps.qcm.domain.model.Status;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.model.category.MpttCategoryRepository;
import com.emu.apps.qcm.domain.model.imports.ImportStatus;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireRepository;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.tag.TagRepository;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.domain.model.upload.UploadRepository;
import com.emu.apps.shared.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.emu.apps.qcm.domain.model.Status.DRAFT;
import static com.emu.apps.qcm.domain.model.imports.ImportStatus.DONE;
import static com.emu.apps.qcm.domain.model.imports.ImportStatus.REJECTED;
import static com.emu.apps.qcm.domain.model.question.TypeQuestion.FREE_TEXT;
import static com.emu.apps.qcm.domain.model.upload.TypeUpload.EXPORT_JSON;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Set.of;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Service
public class ImportService {

    public static final String IMPORT = "import";

    public static final String TYPE_QUESTION = "QUESTION";

    public static final String TYPE_QUESTIONNAIRE = "QUESTIONNAIRE";

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;

    private final UploadRepository uploadRepository;

    private final MpttCategoryRepository mpttCategoryRepository;


    public ImportService(QuestionnaireRepository questionnaireService, QuestionRepository questionService,
                         TagRepository tagRepository, UploadRepository uploadRepository, MpttCategoryRepository mpttCategoryRepository) {
        this.questionnaireRepository = questionnaireService;
        this.questionRepository = questionService;
        this.tagRepository = tagRepository;
        this.uploadRepository = uploadRepository;
        this.mpttCategoryRepository = mpttCategoryRepository;
    }

    @Transactional
    public Upload importFile(UploadId uploadId, PrincipalId principal) throws IOException {

        var upload = uploadRepository.getUploadOfId(uploadId);

        //  use strategy
        if (EXPORT_JSON.name().equals(upload.getType())) {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(WRITE_DATES_AS_TIMESTAMPS)
                    .configure(INDENT_OUTPUT, true);

            var export = mapper.readValue(new ByteArrayInputStream(upload.getData()), Export.class);
            var importStatus = importQuestionnaire(upload.getFileName(), export, principal);
            upload.setStatus(importStatus.name());
        } else {
            final ImportFileQuestion[] fileQuestionDtos = new ObjectMapper().readValue(new ByteArrayInputStream(upload.getData()), ImportFileQuestion[].class);
            ImportStatus importStatus = importQuestionnaire(upload.getFileName(), fileQuestionDtos, principal);
            upload.setStatus(importStatus.name());
        }

        return uploadRepository.saveUpload(upload);

    }

    private Question mapToQuestion(QuestionExport questionExport, MpttCategory mpttCategory) {

        var question = new Question();

        question.setQuestionText(questionExport.getQuestionText());
        question.setType(questionExport.getType());
        question.setStatus(questionExport.getStatus());

        question.setMpttCategory(mpttCategory);

        var responses = new ArrayList <Response>();
        for (var responseExport : questionExport.getResponses()) {

            var response = new Response();
            response.setResponseText(responseExport.getResponseText());
            response.setGood(responseExport.getGood());
            responses.add(response);
        }

        question.setResponses(responses);

        List <QuestionTag> questionTags = new ArrayList <>();

        for (var qtag : questionExport.getQuestionTags()) {
            var questionTagDto = new QuestionTag();
            questionTagDto.setLibelle(qtag.getLibelle());
            questionTags.add(questionTagDto);
        }
        question.setTags(questionTags);
        return question;

    }


    private Question mapToQuestion(ImportFileQuestion importFileQuestion, MpttCategory mpttCategory) {

        var question = new Question();

        question.setQuestionText(importFileQuestion.getQuestion());
        question.setType(FREE_TEXT.name());
        question.setStatus(DRAFT.name());

        question.setMpttCategory(mpttCategory);

        Response response = new Response();
        response.setResponseText(importFileQuestion.getResponse());
        question.setResponses(asList(response));

        QuestionTag questionTag = new QuestionTag();
        questionTag.setLibelle(importFileQuestion.getCategorie());

        question.setTags(new ArrayList <>(asList(questionTag)));

        return question;

    }


    @Transactional
    public ImportStatus importQuestionnaire(String name, Export export, PrincipalId principal) {

        Questionnaire questionnaire = new Questionnaire();

        // questionnaire
        questionnaire.setTitle(export.getQuestionnaire().getTitle());
        questionnaire.setStatus(Status.getByName(export.getQuestionnaire().getStatus()));
        if (StringUtils.isNotBlank(export.getQuestionnaire().getUuid())) {
            questionnaire.setId(new QuestionnaireId(export.getQuestionnaire().getUuid()));
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
        questionnaire.setTags(qtags);

        // categorie
        if (Objects.nonNull(export.getQuestionnaire().getCategory())) {
            MpttCategory mpttCategory = new MpttCategory();
            mpttCategory.setLibelle(export.getQuestionnaire().getCategory().getLibelle());
            mpttCategory.setType(TYPE_QUESTIONNAIRE);
            mpttCategory.setUserId(principal.toUuid());
            mpttCategory = mpttCategoryRepository.saveCategory(mpttCategory, principal);
            questionnaire.setMpttCategory(mpttCategory);
        }

        Questionnaire newQuestionnaire = questionnaireRepository.saveQuestionnaire(questionnaire, principal);

        //questions
        List <Question> questions = export.getQuestions()
                .stream()
                .map(questionExport ->
                {
                    MpttCategory mpttCategory = null;
                    if (Objects.nonNull(questionExport.getCategory())) {
                        mpttCategory = new MpttCategory();
                        mpttCategory.setLibelle(questionExport.getCategory().getLibelle());
                        mpttCategory.setType(TYPE_QUESTION);
                        mpttCategory.setUserId(principal.toUuid());
                        mpttCategory = mpttCategoryRepository.saveCategory(mpttCategory, principal);
                    }

                    return mapToQuestion(questionExport, mpttCategory);
                })
                .collect(toList());

        Collection <Question> questionDtos = questionRepository.saveQuestions(questions, principal);

        questionnaireRepository.addQuestions(new QuestionnaireId(newQuestionnaire.getId().toUuid()), questionDtos, principal);

        return DONE;
    }



    private ImportStatus importQuestionnaire(String name, ImportFileQuestion[] fileQuestions, PrincipalId principal) {

        if (ArrayUtils.isNotEmpty(fileQuestions)) {
            try {
                var questionnaire = new Questionnaire();

                questionnaire.setTitle(IMPORT);
                questionnaire.setStatus(DRAFT);

                final var tagQuestionnaire = tagRepository.findOrCreateByLibelle(IMPORT, principal);

                var questionnaireTag = new QuestionnaireTag();
                questionnaireTag.setUuid(tagQuestionnaire.getId().toUuid());
                questionnaire.setTags(of(questionnaireTag));

                Questionnaire createdQuestionnaire = questionnaireRepository.saveQuestionnaire(questionnaire, principal);

                var questionCategory = new MpttCategory();
                questionCategory.setLibelle(IMPORT);
                questionCategory.setType(TYPE_QUESTION);
                questionCategory.setUserId(principal.toUuid());

                final var category = mpttCategoryRepository.saveCategory(questionCategory, principal);

                var questions = stream(fileQuestions)
                        .filter(fileQuestion -> isNotEmpty(fileQuestion.getCategorie()))
                        .map(fileQuestion -> mapToQuestion(fileQuestion, category))
                        .collect(toList());

                var createdQuestions = questionRepository.saveQuestions(questions, principal);

                questionnaireRepository.addQuestions(createdQuestionnaire.getId(), createdQuestions, principal);

            } catch (TechnicalException e) {

                return REJECTED;
            }

        }

        return DONE;
    }

}
