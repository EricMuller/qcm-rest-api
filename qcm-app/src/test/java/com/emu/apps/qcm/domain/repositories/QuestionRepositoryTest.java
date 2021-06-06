package com.emu.apps.qcm.domain.repositories;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.category.CategoryRepository;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionRepository;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST_ID;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;
import static org.apache.commons.collections4.IterableUtils.isEmpty;
import static org.apache.commons.collections4.IterableUtils.size;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        setPrincipal(USER_TEST_ID.toUuid());
    }

    @Test
    void testGetQuestions() {


        Iterable <QuestionWithTagsOnly> questionTags = questionRepository.getQuestions(new TagId[0], new QuestionnaireId[0], PageRequest.of(0, 10), new PrincipalId("toto"));

        Assertions.assertTrue(isEmpty(questionTags));

    }

    @Test
    void testSaveQuestion() {

        Category category = new Category();
        category.setType(Type.QUESTION.name());
        category.setLibelle("QuestionBusinessPortTest.testSaveQuestion");

        category = categoryRepository.saveCategory(category, USER_TEST_ID);

        Question question = new Question();
        question.setQuestionText("why?");
        question.setCategory(category);

        Response response = new Response();
        response.setResponseText("because");

        question.setResponses(Arrays.asList(response));

        Question saveQuestion = questionRepository.saveQuestion(question, USER_TEST_ID);

        Assertions.assertNotNull(saveQuestion);
        Assertions.assertNotNull(saveQuestion.getId());
        Assertions.assertNotNull(saveQuestion.getId().toUuid());
        Assertions.assertEquals("why?", saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));

        Question getQuestion = questionRepository.getQuestionById(new QuestionId(saveQuestion.getId().toUuid())).orElse(null);
        Assertions.assertNotNull(getQuestion);
        Assertions.assertEquals("why?", saveQuestion.getQuestionText());
        Assertions.assertEquals(1, size(saveQuestion.getResponses()));


        getQuestion.setQuestionText("why2?");
        Question updateQuestion = questionRepository.saveQuestion(getQuestion, USER_TEST_ID);


        Assertions.assertNotNull(updateQuestion);
        Assertions.assertEquals("why2?", updateQuestion.getQuestionText());
    }


}
