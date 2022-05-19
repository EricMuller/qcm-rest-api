package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {QuestionTagRepositoryTest.Initializer.class})
class QuestionTagRepositoryTest {

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
    private QuestionTagRepository questionTagRepository;


    @Test
    void findByQuestionId() {

        final String principal =  UUID.randomUUID().toString();

        dbFixture.emptyDatabase(principal);

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst(principal);

        Iterable <TagQuestionEntity> tags = questionTagRepository.findByQuestionId(question.getId());

        assertThat(tags).isNotNull().isNotEmpty();
        assertThat(tags.spliterator().estimateSize()).isEqualTo(2);

    }

}
