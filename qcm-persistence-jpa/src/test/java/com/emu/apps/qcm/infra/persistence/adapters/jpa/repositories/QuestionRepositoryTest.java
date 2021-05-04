package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.Fixture;
import com.emu.apps.shared.security.PrincipalUtils;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture.QUESTION_TAG_LIBELLE_1;
import static com.google.common.collect.Iterables.getFirst;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {QuestionRepositoryTest.Initializer.class})
@Testcontainers
class QuestionRepositoryTest {

    @Container
    static private final PostgreSQLContainer postgresqlContainer =  new PostgreSQLContainer()
            .withDatabaseName("postgres")
            .withUsername("test")
            .withPassword("test");

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
    private DbFixture dbFixture;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    void findOne() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());
        assertNotNull(newQuestion.orElse(null));
        assertNotNull(newQuestion.orElse(null).getId());
        assertEquals(DbFixture.QUESTION_QUESTION_1, newQuestion.get().getQuestionText());
        assertEquals(DbFixture.QUESTION_TIP_1, newQuestion.get().getTip());
        // Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection Tags")
    void findOneLazyInitializationException() {


        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());

        assertThat(newQuestion).isPresent();

        assertThrows(LazyInitializationException.class, () -> newQuestion.get().getQuestionTags().size());

    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection responses")
    void findByIdAndFetchTagsLazyInitializationException() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThrows(LazyInitializationException.class, () -> newQuestion.getResponses().size());
    }


    @Test
    void findByIdAndFetchTags() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThat(newQuestion).isNotNull();
        assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTagEntity> optional = newQuestion.getQuestionTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        assertThat(optional).isPresent();

    }

    @Test
    void findByIdAndFetchTagsAndResponses() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        //test create
        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTagsAndResponses(question.getId());

        assertThat(newQuestion).isNotNull();

        //tags
        assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTagEntity> optional = newQuestion.getQuestionTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        assertThat(optional).isPresent();

        // responses
        assertThat(newQuestion.getResponses()).isNotEmpty();
        assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        ResponseEntity response = getFirst(newQuestion.getResponses(), null);

        assertThat(response).isNotNull();

    }

    @Test
    void findAllQuestionsTags() {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        Page <QuestionEntity> page = questionRepository.findAllQuestionsTags(null);

        assertThat(page).isNotNull();

        List <QuestionEntity> content = page.getContent();
        assertThat(content).isNotEmpty();

        QuestionEntity first = getFirst(content, null);

        assertThat(first).isNotNull();

        Set <QuestionTagEntity> questionTags = first.getQuestionTags();
        assertThat(questionTags).isNotEmpty();
        assertThat(questionTags.size()).isEqualTo(2);

        QuestionTagEntity questionTag = getFirst(questionTags, null);

        assertThat(questionTag).isNotNull();
        assertThat(questionTag.getTag()).isNotNull();

    }

    @Test
    void findAllStatus() {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        Page <String> page = questionRepository.findAllStatusByCreatedBy(Fixture.USER, null);

        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isNotNull().isEqualTo(1);

    }


    @Test
    void findAllQuestions() {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        TagEntity tag1 = dbFixture.findTagbyLibelle(QUESTION_TAG_LIBELLE_1, () -> USER_TEST.toUUID());
        assertThat(tag1).isNotNull();

        Specification <QuestionEntity> specification = new QuestionEntity.SpecificationBuilder(PrincipalUtils.getEmailOrName((Principal) () -> USER_TEST.toUUID()))
                .setTagUuids(new UUID[]{tag1.getUuid()})
                .build();

        Pageable pageable = of(0, 3, by("id"));

        Page <QuestionEntity> page = questionRepository.findAll(specification, pageable);
        assertThat(page).isNotNull();

        assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
