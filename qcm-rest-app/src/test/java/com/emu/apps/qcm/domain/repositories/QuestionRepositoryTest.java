package com.emu.apps.qcm.domain.repositories;


import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.model.category.MpttCategoryRepository;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static org.apache.commons.collections4.IterableUtils.size;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@ContextConfiguration(initializers = {QuestionRepositoryTest.Initializer.class})
class QuestionRepositoryTest {

    @RegisterExtension
    static BaeldungPostgresqlExtension postgresqlContainer = BaeldungPostgresqlExtension.getInstance();

    static class Initializer
            implements ApplicationContextInitializer <ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MpttCategoryRepository mpttCategoryRepository;

    @Autowired
    private DbRepositoryFixture dbFixture;


    @Test
    void testSaveQuestion() {

        var account = dbFixture.createAccountTest();

        var mpttCategory = new MpttCategory();
        mpttCategory.setType(MpttType.QUESTION.name());
        mpttCategory.setLibelle("QuestionBusinessPortTest.testSaveQuestion");

        final var principalId = new PrincipalId(account.getId().toUuid());

        mpttCategory = mpttCategoryRepository.saveCategory(mpttCategory, principalId);

        var question = new Question();
        question.setQuestionText("why?");
        question.setMpttCategory(mpttCategory);

        var response = new Response();
        response.setResponseText("because");

        question.setResponses(Arrays.asList(response));

        var saveQuestion = questionRepository.saveQuestion(question, principalId);

        Assertions.assertNotNull(saveQuestion);
        Assertions.assertNotNull(saveQuestion.getId());
        Assertions.assertNotNull(saveQuestion.getId().toUuid());
        Assertions.assertEquals("why?", saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));

        var getQuestion = questionRepository.getQuestionOfId(new QuestionId(saveQuestion.getId().toUuid())).orElse(null);
        Assertions.assertNotNull(getQuestion);


        Assertions.assertEquals("why?", saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));


        getQuestion.setQuestionText("why2?");
        var updateQuestion = questionRepository.updateQuestion(getQuestion, principalId);


        Assertions.assertNotNull(updateQuestion);
        Assertions.assertEquals("why2?", updateQuestion.getQuestionText());
    }


}
