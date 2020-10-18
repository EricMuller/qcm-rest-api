package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Response;
import com.emu.apps.qcm.api.models.question.QuestionTags;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class QuestionBusinessPortTest {

    @Autowired
    private QuestionBusinessPort questionBusinessPort;

    @Autowired
    private CategoryBusinessPort categoryBusinessPort;

    @BeforeEach
    void setUp() {
        AuthentificationContextHolder.setUser(SpringBootJpaTestConfig.USER_TEST);
    }

    @Test
    void testGetQuestions() {


        Iterable <QuestionTags> questionTags = questionBusinessPort.getQuestions(new String[0], new String[0], PageRequest.of(0, 10), "toto");

        Assertions.assertTrue(IterableUtils.isEmpty(questionTags));

    }

    @Test
    void testSaveQuestion() throws FunctionnalException {

        Category category = new Category();
        category.setType(Type.QUESTION.name());
        category.setLibelle("QuestionBusinessPortTest.testSaveQuestion");

        category = categoryBusinessPort.saveCategory(category, SpringBootJpaTestConfig.USER_TEST);

        Question question = new Question();
        question.setQuestionText("why?");
        question.setCategory(category);

        Response response = new Response();
        response.setResponseText("because");

        question.setResponses(Arrays.asList(response));

        Question saveQuestion = questionBusinessPort.saveQuestion(question, SpringBootJpaTestConfig.USER_TEST);

        Assertions.assertNotNull(saveQuestion);
        Assertions.assertNotNull(saveQuestion.getUuid());
        Assertions.assertEquals("why?",saveQuestion.getQuestionText());
        Assertions.assertEquals(1, IterableUtils.size(saveQuestion.getResponses()));

        Question getQuestion = questionBusinessPort.getQuestionByUuId(saveQuestion.getUuid());
        Assertions.assertNotNull(getQuestion);
        Assertions.assertEquals("why?",saveQuestion.getQuestionText());
        Assertions.assertEquals(1, IterableUtils.size(saveQuestion.getResponses()));


        getQuestion.setQuestionText("why2?");
        Question updateQuestion = questionBusinessPort.saveQuestion(getQuestion, SpringBootJpaTestConfig.USER_TEST);


        Assertions.assertNotNull(updateQuestion);
        Assertions.assertEquals("why2?",updateQuestion.getQuestionText());
    }


}
