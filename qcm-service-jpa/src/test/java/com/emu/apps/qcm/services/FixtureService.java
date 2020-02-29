package com.emu.apps.qcm.services;


import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.questions.Type;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.services.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.services.jpa.repositories.*;
import com.emu.apps.qcm.services.jpa.repositories.category.CategoryRepository;
import com.emu.apps.shared.security.PrincipalUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static com.emu.apps.qcm.services.entity.category.Category.Type.QUESTION;
import static com.emu.apps.qcm.services.entity.category.Category.Type.QUESTIONNAIRE;

@Component
@Slf4j
public class FixtureService {

    public static final String QUESTION_QUESTION_1 = "a cool question";

    public static final String QUESTION_QUESTION_2 = "a cool question 2";

    public static final String RESPONSE_RESPONSE_1 = "a cool response 1";

    public static final String RESPONSE_RESPONSE_2 = "a cool response 2";

    public static final String QUESTION_TAG_LIBELLE_1 = "Tag1";

    public static final String QUESTION_TAG_LIBELLE_2 = "Tag2";

    public static final String QUESTION_TAG_LIBELLE_3 = "Tag3";

    public static final String QUESTIONNAIRE_TAG_LIBELLE_1 = "Tag10";

    public static final String CATEGORIE_LIBELLE = "Categ lib";

    public static final String QUESTIONNAIRE_TITLE = "Questionnaire";

    public static final String QUESTIONNAIRE_DESC = "Questionnaire desc";


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionnaireRepository questionnaireJpaRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResponseRepository responseCrudRepository;

    @Autowired
    private QuestionnaireTagRepository questionnaireTagRepository;

    @Autowired
    private UploadRepository uploadRepository;


    public FixtureService() {
    }

    @Transactional(readOnly = true)
    public Tag findTagbyLibelle(String name, Principal principal) {
        return tagRepository.findByLibelle(name, PrincipalUtils.getEmail(principal));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Questionnaire createOneQuestionnaireWithTwoQuestionTags() {

        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();
        categoryRepository.deleteAll();
        questionnaireJpaRepository.deleteAll();
        categoryRepository.deleteAll();

        // category
        Category questionCategory = new Category(QUESTION, CATEGORIE_LIBELLE);
        questionCategory = categoryRepository.save(questionCategory);

        // category
        Category questionnaireCategory = new Category(QUESTIONNAIRE, CATEGORIE_LIBELLE);
        questionnaireCategory = categoryRepository.save(questionnaireCategory);

        //questionnaire
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(QUESTIONNAIRE_TITLE);
        questionnaire.setDescription(QUESTIONNAIRE_DESC);
        questionnaire.setCategory(questionnaireCategory);
        questionnaire = questionnaireJpaRepository.save(questionnaire);

        Response response1 = new Response();
        response1.setNumber(1l);
        response1.setResponse(RESPONSE_RESPONSE_1);
        response1 = responseCrudRepository.save(response1);

        Response response2 = new Response();
        response2.setNumber(2l);
        response2.setResponse(RESPONSE_RESPONSE_2);
        response2 = responseCrudRepository.save(response2);

        //question 1
        Question question1 = new Question();
        question1.setQuestion(QUESTION_QUESTION_1);
        question1.setType(Type.FREE_TEXT);
        question1.setCategory(questionCategory);

        // question.setQuestionnaire(questionnaire);
        question1.setResponses(Lists.newArrayList(response1, response2));
        question1 = questionRepository.save(question1);

        //question 2
        Question question2 = new Question();
        question2.setQuestion(QUESTION_QUESTION_2);
        question2.setType(Type.FREE_TEXT);
        question2.setCategory(questionCategory);

        question2 = questionRepository.save(question2);

        //questionTag
        Tag tag1 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_1, false));
        Tag tag2 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_2, false));

        questionTagRepository.save(new QuestionTag(question1, tag1));
        questionTagRepository.save(new QuestionTag(question1, tag2));
        questionTagRepository.save(new QuestionTag(question2, tag1));
        questionTagRepository.save(new QuestionTag(question2, tag2));


        questionnaireQuestionRepository.save(new QuestionnaireQuestion(questionnaire, question1, 1L));

        questionnaireQuestionRepository.save(new QuestionnaireQuestion(questionnaire, question2, 2L));

        //questionnaireTag
        Tag questionnaireTag1 = tagRepository.save(new Tag(QUESTIONNAIRE_TAG_LIBELLE_1, false));
        questionnaireTagRepository.save(new QuestionnaireTagBuilder()
                .setQuestionnaire(questionnaire)
                .setTag(questionnaireTag1)
                .build());

        return questionnaire;

    }

    public Upload createUpload() {
        Upload upload = new Upload();
        upload.setFileName("fileName");
        upload.setPathfileName("/data/");
        return uploadRepository.save(upload);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Question createQuestionsAndGetFirst() {

        questionnaireQuestionRepository.deleteAll();
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();

//
//        Choice choice = new Choice("message", 1L, true);
//        Choice choice2 = new Choice("message", 2L, false);

        Response response = new Response();
        response.setNumber(1l);
        response.setResponse(RESPONSE_RESPONSE_1);
//        response.setChoices(Sets.newHashSet(choice, choice2));
        responseCrudRepository.save(response);

        Response response2 = new Response();
        response2.setNumber(2l);
        response2.setResponse(RESPONSE_RESPONSE_2);
        responseCrudRepository.save(response2);

        //question 1
        Question question1 = new Question();
        question1.setQuestion(QUESTION_QUESTION_1);
        question1.setType(Type.FREE_TEXT);

        question1.setResponses(Lists.newArrayList(response, response2));
        questionRepository.save(question1);

        //question 2
        Question question2 = new Question();
        question2.setQuestion(QUESTION_QUESTION_2);
        question2.setType(Type.FREE_TEXT);

        questionRepository.save(question2);

        //questionTag
        Tag tag1 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_1, false));
        Tag tag2 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_2, false));
        Tag tag3 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_3, false));


        questionTagRepository.save(new QuestionTag(question1, tag1));
        questionTagRepository.save(new QuestionTag(question1, tag2));
        questionTagRepository.save(new QuestionTag(question2, tag1));
        questionTagRepository.save(new QuestionTag(question2, tag3));

        return question1;

    }

}
