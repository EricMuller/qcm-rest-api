package com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures;


import com.emu.apps.qcm.infra.persistence.MpptCategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.TagQuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
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
import java.util.UUID;

import static com.emu.apps.qcm.domain.model.question.TypeQuestion.FREE_TEXT;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTIONNAIRE;
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
    private TagQuestionnaireRepository tagQuestionnaireRepository;

    @Autowired
    private TagQuestionRepository tagQuestionRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private MpttCategoryRepository mpttCategoryRepository;

    @Autowired
    private ResponseRepository responseCrudRepository;

    @Autowired
    private QuestionnaireTagRepository questionnaireTagRepository;

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private WebHookRepository webHookRepository;

    @Autowired
    private MpptCategoryPersistencePort mpptCategoryPersistencePort;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public TagQuestionEntity findTagQuestionbyLibelle(String name, Principal principal) {
        return tagQuestionRepository.findByLibelle(name, PrincipalUtils.getEmailOrName(principal));
    }

    @Transactional(readOnly = true)
    public TagQuestionEntity findTagQuestionbyLibelle(String name, String principal) {
        return tagQuestionRepository.findByLibelle(name, principal);
    }

    @Transactional(readOnly = true)
    public TagQuestionnaireEntity findTagQuestionnairebyLibelle(String name, Principal principal) {
        return tagQuestionnaireRepository.findByLibelle(name, PrincipalUtils.getEmailOrName(principal));
    }

    @Transactional(readOnly = true)
    public TagQuestionnaireEntity findTagQuestionnairebyLibelle(String name, String principal) {
        return tagQuestionnaireRepository.findByLibelle(name, principal);
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void emptyDatabase() {
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteAll();
        tagQuestionRepository.deleteAll();
        questionnaireRepository.deleteAll();
        entityManager.flush();
        mpttCategoryRepository.deleteAll();
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
        tagQuestionRepository.deleteByCreatedByEquals(userId);
        questionnaireRepository.deleteByCreatedByEquals(userId);

        mpttCategoryRepository.deleteAllByUser(userId);
        accountRepository.deleteByEmailEquals(userId);
        entityManager.flush();
        // addUserTest(USER);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAndSaveUserWithEmail(UUID uuid, String email) {
        AccountEntity accountEntity = new AccountEntity(uuid);
        accountEntity.setEmail(email);
        accountRepository.save(accountEntity);
        entityManager.flush();
    }

    private AccountEntity getAccountByUserName(String uuid){
       var accountEntityOptional = accountRepository.findByUuid(UUID.fromString(uuid));
        AccountEntity accountEntity;
        if(accountEntityOptional.isEmpty()){
            //accountEntity = new AccountEntity(fromString(uuid));
            accountEntity = new AccountEntity(fromString(uuid));
            //accountEntity.setId(fromString(uuid));
            accountEntity.setUserName(uuid);
            accountEntity = accountRepository.save(accountEntity);
        }else{
            accountEntity = accountEntityOptional.get();
        }
        return accountEntity;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionnaireEntity createOneQuestionnaireWithTwoQuestionTags(String accountUuid) {
        try {

            var accountEntity = getAccountByUserName(accountUuid);

            var questionnaireCategory = mpptCategoryPersistencePort.findOrCreateByLibelle(accountUuid, QUESTIONNAIRE.name(), CATEGORIE_LIBELLE);

            var questionCategory = mpptCategoryPersistencePort.findOrCreateByLibelle(accountUuid, QUESTION.name(), CATEGORIE_LIBELLE);

            var questionnaireCategoryEntity = mpttCategoryRepository.findByUuid(fromString(questionnaireCategory.getId().toUuid()));

            var questionCategoryEntity = mpttCategoryRepository.findByUuid(fromString(questionCategory.getId().toUuid()));

            //questionnaire
            var questionnaireEntity = new QuestionnaireEntity();
            questionnaireEntity.setTitle(QUESTIONNAIRE_TITLE);
            questionnaireEntity.setDescription(QUESTIONNAIRE_DESC);
            questionnaireEntity.setCategory(questionnaireCategoryEntity);
            questionnaireEntity.setCreatedBy(accountUuid);
            questionnaireEntity = questionnaireRepository.save(questionnaireEntity);


            var responseEntity1 = new ResponseEntity();
            responseEntity1.setNumber(1l);
            responseEntity1.setResponseText(RESPONSE_RESPONSE_1);
            responseEntity1.setCreatedBy(accountUuid);
            responseEntity1 = responseCrudRepository.save(responseEntity1);


            var responseEntity2 = new ResponseEntity();
            responseEntity2.setNumber(2l);
            responseEntity2.setResponseText(RESPONSE_RESPONSE_2);
            responseEntity2.setCreatedBy(accountUuid);
            responseEntity2 = responseCrudRepository.save(responseEntity2);
            responseEntity2.setCreatedBy(accountUuid);

            //question 1
            var questionEntity1 = new QuestionEntity();
            questionEntity1.setQuestionText(QUESTION_QUESTION_1);
            questionEntity1.setType(FREE_TEXT);
            questionEntity1.setCreatedBy(accountUuid);
            questionEntity1.setMpttCategory(questionCategoryEntity);
            questionEntity1.setOwner(accountEntity);

            // question.setQuestionnaire(questionnaire);
            responseEntity1.setQuestion(questionEntity1);
            responseEntity2.setQuestion(questionEntity1);
            questionEntity1.setResponses(List.of(responseEntity1, responseEntity2));
            questionEntity1 = questionRepository.save(questionEntity1);

            //question 2
            var questionEntity2 = new QuestionEntity();
            questionEntity2.setQuestionText(QUESTION_QUESTION_2);
            questionEntity2.setType(FREE_TEXT);
            questionEntity2.setMpttCategory(questionCategoryEntity);
            questionEntity2.setCreatedBy(accountUuid);
            questionEntity2.setOwner(accountEntity);
            questionEntity2 = questionRepository.save(questionEntity2);

            //questionTag
            var tagQuestionEntity1 = tagQuestionRepository.save(new TagQuestionEntity(QUESTION_TAG_LIBELLE_1,  accountUuid));
            var tagQuestionEntity2 = tagQuestionRepository.save(new TagQuestionEntity(QUESTION_TAG_LIBELLE_2,  accountUuid));

            questionTagRepository.save(new QuestionTagEntity(questionEntity1, tagQuestionEntity1));
            questionTagRepository.save(new QuestionTagEntity(questionEntity1, tagQuestionEntity2));
            questionTagRepository.save(new QuestionTagEntity(questionEntity2, tagQuestionEntity1));
            questionTagRepository.save(new QuestionTagEntity(questionEntity2, tagQuestionEntity2));

            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity1, 1));

            questionnaireQuestionRepository.save(new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity2, 2));

            //questionnaireTag
            var tagQuestionnaireEntity = tagQuestionnaireRepository.save(new TagQuestionnaireEntity(QUESTIONNAIRE_TAG_LIBELLE_1,  accountUuid));

            questionnaireTagRepository.save(new QuestionnaireTagBuilder()
                    .setQuestionnaire(questionnaireEntity)
                    .setTag(tagQuestionnaireEntity)
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

    public AccountEntity createAccount(String email, UUID uuid) {
        AccountEntity accountEntity = new AccountEntity(uuid);
        accountEntity.setEmail(email);
        // user.setCreatedBy(principal);
        return accountRepository.save(accountEntity);
    }

    public WebHookEntity createWebhook(AccountEntity owner, String principal) {
        WebHookEntity webhook = new WebHookEntity();
        webhook.setSecret("secret");
        webhook.setContentType("content-type");
        webhook.setOwner(owner);
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
        TagQuestionEntity tag1 = tagQuestionRepository.save(new TagQuestionEntity(QUESTION_TAG_LIBELLE_1,  accountId));
        TagQuestionEntity tag2 = tagQuestionRepository.save(new TagQuestionEntity(QUESTION_TAG_LIBELLE_2,  accountId));
        TagQuestionEntity tag3 = tagQuestionRepository.save(new TagQuestionEntity(QUESTION_TAG_LIBELLE_3,  accountId));


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
