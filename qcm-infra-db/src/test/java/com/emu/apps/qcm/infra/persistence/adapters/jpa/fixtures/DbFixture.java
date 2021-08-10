package com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures;


import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.upload.UploadEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.*;
import com.emu.apps.shared.security.PrincipalUtils;
import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;

import static com.emu.apps.qcm.domain.model.question.TypeQuestion.FREE_TEXT;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static java.util.UUID.fromString;


@Component
@Slf4j
@NoArgsConstructor
public class DbFixture extends Fixture {


    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

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
    private CategoryPersistencePort categoryPersistencePort;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public TagEntity findTagbyLibelle(String name, Principal principal) {
        return tagRepository.findByLibelle(name, PrincipalUtils.getEmailOrName(principal));
    }

    @Transactional(readOnly = true)
    public TagEntity findTagbyLibelle(String name, String principal) {
        return tagRepository.findByLibelle(name, principal);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void emptyDatabase() {
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();
        questionnaireRepository.deleteAll();
        entityManager.flush();
        categoryRepository.deleteAll();
        accountRepository.deleteAll();
        // addUserTest(USER);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void emptyDatabase(String userId) {

        responseCrudRepository.deleteByCreatedByEquals(userId);
        // questionTagRepository.deleteAll();
        // questionnaireTagRepository.deleteAll();
        // questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteByCreatedByEquals(userId);
        tagRepository.deleteByCreatedByEquals(userId);
        questionnaireRepository.deleteByCreatedByEquals(userId);

        categoryRepository.deleteAllByUser(userId);
        accountRepository.deleteByEmailEquals(userId);
        entityManager.flush();
        // addUserTest(USER);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAndSaveUserWithEmail(String email) {
        accountRepository.save(new AccountEntity(email));
        entityManager.flush();
    }

    private AccountEntity getAccountByUserName(String uuid){
       var accountEntityOptional = accountRepository.findByUserNameEquals(uuid);
        AccountEntity accountEntity;
        if(accountEntityOptional.isEmpty()){
            accountEntity = new AccountEntity(fromString(uuid));
            accountEntity.setId(fromString(uuid));
            accountEntity.setUserName(uuid);
            accountEntity = accountRepository.save(accountEntity);
        }else{
            accountEntity = accountEntityOptional.get();
        }
        return accountEntity;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionnaireEntity createOneQuestionnaireWithTwoQuestionTags(String userUuid) {
        try {

            var accountEntity = getAccountByUserName(userUuid);

            var questionnaireCategory = categoryPersistencePort.findOrCreateByLibelle(userUuid, QUESTIONNAIRE.name(), CATEGORIE_LIBELLE);

            var questionCategory = categoryPersistencePort.findOrCreateByLibelle(userUuid, QUESTION.name(), CATEGORIE_LIBELLE);

            var questionnaireCategoryEntity = categoryRepository.findByUuid(fromString(questionnaireCategory.getId().toUuid()));

            var questionCategoryEntity = categoryRepository.findByUuid(fromString(questionCategory.getId().toUuid()));

            //questionnaire
            var questionnaireEntity = new QuestionnaireEntity();
            questionnaireEntity.setTitle(QUESTIONNAIRE_TITLE);
            questionnaireEntity.setDescription(QUESTIONNAIRE_DESC);
            questionnaireEntity.setCategory(questionnaireCategoryEntity);
            questionnaireEntity.setCreatedBy(userUuid);
            questionnaireEntity = questionnaireRepository.save(questionnaireEntity);


            var responseEntity1 = new ResponseEntity();
            responseEntity1.setNumber(1l);
            responseEntity1.setResponseText(RESPONSE_RESPONSE_1);
            responseEntity1.setCreatedBy(userUuid);
            responseEntity1 = responseCrudRepository.save(responseEntity1);


            var responseEntity2 = new ResponseEntity();
            responseEntity2.setNumber(2l);
            responseEntity2.setResponseText(RESPONSE_RESPONSE_2);
            responseEntity2.setCreatedBy(userUuid);
            responseEntity2 = responseCrudRepository.save(responseEntity2);
            responseEntity2.setCreatedBy(userUuid);

            //question 1
            var questionEntity1 = new QuestionEntity();
            questionEntity1.setQuestionText(QUESTION_QUESTION_1);
            questionEntity1.setType(FREE_TEXT);
            questionEntity1.setCreatedBy(userUuid);
            questionEntity1.setCategory(questionCategoryEntity);
            questionEntity1.setAccount(accountEntity);

            // question.setQuestionnaire(questionnaire);
            responseEntity1.setQuestion(questionEntity1);
            responseEntity2.setQuestion(questionEntity1);
            questionEntity1.setResponses(List.of(responseEntity1, responseEntity2));
            questionEntity1 = questionRepository.save(questionEntity1);

            //question 2
            var questionEntity2 = new QuestionEntity();
            questionEntity2.setQuestionText(QUESTION_QUESTION_2);
            questionEntity2.setType(FREE_TEXT);
            questionEntity2.setCategory(questionCategoryEntity);
            questionEntity2.setCreatedBy(userUuid);
            questionEntity2.setAccount(accountEntity);
            questionEntity2 = questionRepository.save(questionEntity2);

            //questionTag
            var tagEntity1 = tagRepository.save(new TagEntity(QUESTION_TAG_LIBELLE_1, false, userUuid));
            var tagEntity2 = tagRepository.save(new TagEntity(QUESTION_TAG_LIBELLE_2, false, userUuid));

            questionTagRepository.save(new QuestionTagEntity(questionEntity1, tagEntity1));
            questionTagRepository.save(new QuestionTagEntity(questionEntity1, tagEntity2));
            questionTagRepository.save(new QuestionTagEntity(questionEntity2, tagEntity1));
            questionTagRepository.save(new QuestionTagEntity(questionEntity2, tagEntity2));

            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity1, 1));

            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity2, 2));

            //questionnaireTag
            var tagEntity = tagRepository.save(new TagEntity(QUESTIONNAIRE_TAG_LIBELLE_1, false, userUuid));
            questionnaireTagRepository.save(new QuestionnaireTagBuilder()
                    .setQuestionnaire(questionnaireEntity)
                    .setTag(tagEntity)
                    .build());

            entityManager.flush();
            return questionnaireEntity;

        } catch (Exception e) {
            throw new FixtureException(e);
        }
    }

    public UploadEntity createUpload() {
        UploadEntity upload = new UploadEntity();
        upload.setFileName("fileName");
        upload.setPathfileName("/data/");
        return uploadRepository.save(upload);
    }

    public AccountEntity createUser(String email, String principal) {
        AccountEntity user = new AccountEntity();
        user.setEmail(email);
        // user.setCreatedBy(principal);
        return accountRepository.save(user);
    }

    public WebHookEntity createWebhook(AccountEntity accountEntity, String principal) {
        WebHookEntity webhook = new WebHookEntity();
        webhook.setSecret("secret");
        webhook.setContentType("content-type");
        webhook.setUser(accountEntity);
        webhook.setCreatedBy(principal);
        return webHookRepository.save(webhook);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionEntity createQuestionsAndGetFirst(String accountId) {

//        questionnaireQuestionRepository.deleteAll();
//        responseCrudRepository.deleteAll();
//        questionTagRepository.deleteAll();
//        questionnaireTagRepository.deleteAll();
//        questionRepository.deleteAll();
//        tagRepository.deleteAll();

//
//        Choice choice = new Choice("message", 1L, true);
//        Choice choice2 = new Choice("message", 2L, false);

        var accountEntity = getAccountByUserName(accountId);

        ResponseEntity response = new ResponseEntity();
        response.setNumber(1l);
        response.setResponseText(RESPONSE_RESPONSE_1);
        response.setCreatedBy(accountId);
//        response.setChoices(Sets.newHashSet(choice, choice2));
        responseCrudRepository.save(response);

        ResponseEntity response2 = new ResponseEntity();
        response2.setNumber(2l);
        response2.setResponseText(RESPONSE_RESPONSE_2);
        response2.setCreatedBy(accountId);
        responseCrudRepository.save(response2);

        //question 1
        QuestionEntity question1 = new QuestionEntity();
        question1.setQuestionText(QUESTION_QUESTION_1);
        question1.setType(FREE_TEXT);
        question1.setTip(QUESTION_TIP_1);
        question1.setCreatedBy(accountId);

        question1.setResponses(Lists.newArrayList(response, response2));
        questionRepository.save(question1);

        //question 2
        QuestionEntity question2 = new QuestionEntity();
        question2.setQuestionText(QUESTION_QUESTION_2);
        question2.setType(FREE_TEXT);
        question2.setCreatedBy(accountId);

        questionRepository.save(question2);

        //questionTag
        TagEntity tag1 = tagRepository.save(new TagEntity(QUESTION_TAG_LIBELLE_1, false, accountId));
        TagEntity tag2 = tagRepository.save(new TagEntity(QUESTION_TAG_LIBELLE_2, false, accountId));
        TagEntity tag3 = tagRepository.save(new TagEntity(QUESTION_TAG_LIBELLE_3, false, accountId));


        questionTagRepository.save(new QuestionTagEntity(question1, tag1));
        questionTagRepository.save(new QuestionTagEntity(question1, tag2));
        questionTagRepository.save(new QuestionTagEntity(question2, tag1));
        questionTagRepository.save(new QuestionTagEntity(question2, tag3));

        return question1;

    }


    public static class FixtureException extends RuntimeException {


        public FixtureException(Throwable cause) {
            super(cause);
        }
    }
}
