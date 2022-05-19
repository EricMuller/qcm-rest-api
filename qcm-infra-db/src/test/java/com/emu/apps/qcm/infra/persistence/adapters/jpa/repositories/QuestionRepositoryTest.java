package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.Fixture;
import com.emu.apps.shared.security.PrincipalUtils;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
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

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture.QUESTION_TAG_LIBELLE_1;
import static com.google.common.collect.Iterables.getFirst;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
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
    private DbFixture dbFixture;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    void findOne() {

        final String principal =  UUID.randomUUID().toString();

        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);

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

        final String principal =  UUID.randomUUID().toString();

        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());

        assertThat(newQuestion).isPresent();

        assertThrows(LazyInitializationException.class, () -> newQuestion.get().getTags().size());

    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection responses")
    void findByIdAndFetchTagsLazyInitializationException() {

        final String principal =  UUID.randomUUID().toString();
        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThrows(LazyInitializationException.class, () -> newQuestion.getResponses().size());
    }


    @Test
    void findByIdAndFetchTags() {

        final String principal =UUID.randomUUID().toString();
        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThat(newQuestion).isNotNull();
        assertThat(newQuestion.getTags()).isNotEmpty();
        assertThat(newQuestion.getTags()).hasSize(2);

        Optional <QuestionTagEntity> optional = newQuestion.getTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        assertThat(optional).isPresent();

    }

    @Test
    void findByIdAndFetchTagsAndResponses() {

        final String principal =  UUID.randomUUID().toString();
        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);

        //test create
        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTagsAndResponses(question.getId());

        assertThat(newQuestion).isNotNull();

        //tags
        assertThat(newQuestion.getTags()).isNotEmpty();
        assertThat(newQuestion.getTags()).hasSize(2);

        Optional <QuestionTagEntity> optional = newQuestion.getTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        assertThat(optional).isPresent();

        // responses
        assertThat(newQuestion.getResponses()).isNotEmpty();
        assertThat(newQuestion.getTags()).hasSize(2);

        ResponseEntity response = getFirst(newQuestion.getResponses(), null);

        assertThat(response).isNotNull();

    }

    @Test
    void findAllQuestionsTags() {

        // dbFixture.emptyDatabase();
        final String principal = UUID.randomUUID().toString();
        dbFixture.emptyDatabase(principal);

        dbFixture.createOneQuestionnaireWithTwoQuestionTags(principal);

        Page <QuestionEntity> page = questionRepository.findAllQuestionsTags(null);

        assertThat(page).isNotNull();

        List <QuestionEntity> content = page.getContent();
        assertThat(content).isNotEmpty();

        QuestionEntity first = getFirst(content, null);

        assertThat(first).isNotNull();

        Set <QuestionTagEntity> questionTags = first.getTags();
        assertThat(questionTags).isNotEmpty();
        assertThat(questionTags).hasSize(2);

        QuestionTagEntity questionTag = getFirst(questionTags, null);

        assertThat(questionTag).isNotNull();
        assertThat(questionTag.getTag()).isNotNull();

    }

    @Test
    void findAllStatus() {

        final String principal = UUID.randomUUID().toString();

        dbFixture.emptyDatabase(principal);

        dbFixture.createOneQuestionnaireWithTwoQuestionTags(principal);

        Page <String> page = questionRepository.findAllStatusByCreatedBy(Fixture.USER, null);

        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isEqualTo(1);

    }


    @Test
    void findAllQuestions() {

        final String userUuid = UUID.randomUUID().toString();

        dbFixture.emptyDatabase(userUuid);

        dbFixture.createOneQuestionnaireWithTwoQuestionTags(userUuid);

        TagQuestionEntity tag1 = dbFixture.findTagQuestionbyLibelle(QUESTION_TAG_LIBELLE_1,  userUuid);
        assertThat(tag1).isNotNull();

        Specification <QuestionEntity> specification = new QuestionEntity.SpecificationBuilder(PrincipalUtils.getEmailOrName((Principal) () -> userUuid))
                .setTagUuids(new UUID[]{tag1.getUuid()})
                .build();

        Pageable pageable = of(0, 3, by("id"));

        Page <QuestionEntity> page = questionRepository.findAll(specification, pageable);
        assertThat(page).isNotNull();

        assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
