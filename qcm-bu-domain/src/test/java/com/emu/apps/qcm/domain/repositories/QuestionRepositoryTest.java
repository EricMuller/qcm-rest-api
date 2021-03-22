package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.question.Question;
import com.emu.apps.qcm.domain.models.question.QuestionId;
import com.emu.apps.qcm.domain.models.question.Response;

import com.emu.apps.qcm.domain.models.question.QuestionTags;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.shared.exceptions.FunctionnalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;
import static org.apache.commons.collections4.IterableUtils.isEmpty;
import static org.apache.commons.collections4.IterableUtils.size;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionBusinessPort;

    @Autowired
    private CategoryRepository categoryBusinessPort;

    @BeforeEach
    void setUp() {
        setPrincipal(USER_TEST.toUUID());
    }

    @Test
    void testGetQuestions() {


        Iterable <QuestionTags> questionTags = questionBusinessPort.getQuestions(new String[0], new String[0], PageRequest.of(0, 10), new PrincipalId("toto"));

        Assertions.assertTrue(isEmpty(questionTags));

    }

    @Test
    void testSaveQuestion() throws FunctionnalException {

        Category category = new Category();
        category.setType(Type.QUESTION.name());
        category.setLibelle("QuestionBusinessPortTest.testSaveQuestion");

        category = categoryBusinessPort.saveCategory(category, USER_TEST);

        Question question = new Question();
        question.setQuestionText("why?");
        question.setCategory(category);

        Response response = new Response();
        response.setResponseText("because");

        question.setResponses(Arrays.asList(response));

        Question saveQuestion = questionBusinessPort.saveQuestion(question, USER_TEST);

        Assertions.assertNotNull(saveQuestion);
        Assertions.assertNotNull(saveQuestion.getUuid());
        Assertions.assertEquals("why?",saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));

        Question getQuestion = questionBusinessPort.getQuestionById(new QuestionId(saveQuestion.getUuid())).orElse(null);
        Assertions.assertNotNull(getQuestion);
        Assertions.assertEquals("why?",saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));


        getQuestion.setQuestionText("why2?");
        Question updateQuestion = questionBusinessPort.saveQuestion(getQuestion, USER_TEST);


        Assertions.assertNotNull(updateQuestion);
        Assertions.assertEquals("why2?",updateQuestion.getQuestionText());
    }


}
