package com.emu.apps.qcm.spi.infrastructure;


import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.UploadEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.UserEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.*;
import com.emu.apps.qcm.spi.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.spi.persistence.mappers.CategoryMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.UUID;

import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.TypeQuestionEnum.FREE_TEXT;


@Component
@Slf4j
public class DbFixture {

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

    public static final String CATEGORY_LIBELLE = "Category libelle";

    public static final String USER = SpringBootTestConfig.USER_TEST;

    @PersistenceContext
    public EntityManager entityManager;

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

    @Autowired
    private WebHookRepository webHookRepository;

    @Autowired
    private CategoryPersistencePort categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserRepository userRepository;

    public DbFixture() {
    }

    @Transactional(readOnly = true)
    public Tag findTagbyLibelle(String name, Principal principal) {
        return tagRepository.findByLibelle(name, PrincipalUtils.getEmail(principal));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void emptyDatabase() {
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();
        questionnaireJpaRepository.deleteAll();
        entityManager.flush();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
        UserEntity user = new UserEntity();
        user.setEmail(USER);
        userRepository.save(user);
        entityManager.flush();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionnaireEntity createOneQuestionnaireWithTwoQuestionTags() {

        try {

            Category questionnaireCategoryDto = categoryService.findOrCreateByLibelle(USER, QUESTIONNAIRE, CATEGORIE_LIBELLE);

            Category questionCategoryDto = categoryService.findOrCreateByLibelle(USER, QUESTION, CATEGORIE_LIBELLE);

            CategoryEntity questionnaireCategory = categoryRepository.findByUuid(UUID.fromString(questionnaireCategoryDto.getUuid()));

            CategoryEntity questionCategory = categoryRepository.findByUuid(UUID.fromString(questionCategoryDto.getUuid()));

            //questionnaire
            QuestionnaireEntity questionnaire = new QuestionnaireEntity();
            questionnaire.setTitle(QUESTIONNAIRE_TITLE);
            questionnaire.setDescription(QUESTIONNAIRE_DESC);
            questionnaire.setCategory(questionnaireCategory);
            questionnaire = questionnaireJpaRepository.save(questionnaire);

            ResponseEntity response1 = new ResponseEntity();
            response1.setNumber(1l);
            response1.setResponse(RESPONSE_RESPONSE_1);
            response1 = responseCrudRepository.save(response1);

            ResponseEntity response2 = new ResponseEntity();
            response2.setNumber(2l);
            response2.setResponse(RESPONSE_RESPONSE_2);
            response2 = responseCrudRepository.save(response2);

            //question 1
            QuestionEntity question1 = new QuestionEntity();
            question1.setQuestion(QUESTION_QUESTION_1);
            question1.setType(FREE_TEXT);
            question1.setCategory(questionCategory);

            // question.setQuestionnaire(questionnaire);
            question1.setResponses(Lists.newArrayList(response1, response2));
            question1 = questionRepository.save(question1);

            //question 2
            QuestionEntity question2 = new QuestionEntity();
            question2.setQuestion(QUESTION_QUESTION_2);
            question2.setType(FREE_TEXT);
            question2.setCategory(questionCategory);

            question2 = questionRepository.save(question2);

            //questionTag
            Tag tag1 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_1, false));
            Tag tag2 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_2, false));

            questionTagRepository.save(new QuestionTagEntity(question1, tag1));
            questionTagRepository.save(new QuestionTagEntity(question1, tag2));
            questionTagRepository.save(new QuestionTagEntity(question2, tag1));
            questionTagRepository.save(new QuestionTagEntity(question2, tag2));


            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaire, question1, 1));

            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaire, question2, 2));

            //questionnaireTag
            Tag questionnaireTag1 = tagRepository.save(new Tag(QUESTIONNAIRE_TAG_LIBELLE_1, false));
            questionnaireTagRepository.save(new QuestionnaireTagBuilder()
                    .setQuestionnaire(questionnaire)
                    .setTag(questionnaireTag1)
                    .build());

            return questionnaire;

        } catch (Exception e) {
            throw new FixtureException();
        }
    }

    public UploadEntity createUpload() {
        UploadEntity upload = new UploadEntity();
        upload.setFileName("fileName");
        upload.setPathfileName("/data/");
        return uploadRepository.save(upload);
    }

    public UserEntity createUser(String email) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        return userRepository.save(user);
    }

    public WebHookEntity createWebhook(UserEntity user) {
        WebHookEntity webhook = new WebHookEntity();
        webhook.setSecret("secret");
        webhook.setContentType("content-type");
        webhook.setUser(user);
        return webHookRepository.save(webhook);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionEntity createQuestionsAndGetFirst() {

        questionnaireQuestionRepository.deleteAll();
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();

//
//        Choice choice = new Choice("message", 1L, true);
//        Choice choice2 = new Choice("message", 2L, false);

        ResponseEntity response = new ResponseEntity();
        response.setNumber(1l);
        response.setResponse(RESPONSE_RESPONSE_1);
//        response.setChoices(Sets.newHashSet(choice, choice2));
        responseCrudRepository.save(response);

        ResponseEntity response2 = new ResponseEntity();
        response2.setNumber(2l);
        response2.setResponse(RESPONSE_RESPONSE_2);
        responseCrudRepository.save(response2);

        //question 1
        QuestionEntity question1 = new QuestionEntity();
        question1.setQuestion(QUESTION_QUESTION_1);
        question1.setType(FREE_TEXT);

        question1.setResponses(Lists.newArrayList(response, response2));
        questionRepository.save(question1);

        //question 2
        QuestionEntity question2 = new QuestionEntity();
        question2.setQuestion(QUESTION_QUESTION_2);
        question2.setType(FREE_TEXT);

        questionRepository.save(question2);

        //questionTag
        Tag tag1 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_1, false));
        Tag tag2 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_2, false));
        Tag tag3 = tagRepository.save(new Tag(QUESTION_TAG_LIBELLE_3, false));


        questionTagRepository.save(new QuestionTagEntity(question1, tag1));
        questionTagRepository.save(new QuestionTagEntity(question1, tag2));
        questionTagRepository.save(new QuestionTagEntity(question2, tag1));
        questionTagRepository.save(new QuestionTagEntity(question2, tag3));

        return question1;

    }


    public static class FixtureException extends RuntimeException {
    }
}
