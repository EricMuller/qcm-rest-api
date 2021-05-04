package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;


import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static java.util.stream.Collectors.toList;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {CategoryRepositoryTest.Initializer.class})
public class CategoryRepositoryTest {

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

    private static final PrincipalId USER_TEST = SpringBootJpaTestConfig.USER_TEST;

    @Autowired
    private CategoryPersistencePort categoryService;

    @Autowired
    private DbFixture dbFixture;


    @Test
    @Transactional
    public void findOrCreateByLibelle() {

        dbFixture.emptyDatabase();


        Category categoryA = categoryService.findOrCreateByLibelle(USER_TEST.toUUID(), QUESTIONNAIRE.name(), "InterviewsA");
        Category categoryB = categoryService.findOrCreateByLibelle(USER_TEST.toUUID(), QUESTIONNAIRE.name(), "InterviewsB");
        Category categoryC = categoryService.findOrCreateByLibelle(USER_TEST.toUUID(), QUESTIONNAIRE.name(), "InterviewsC");

        Category categoryC2 = categoryService.findOrCreateByLibelle(USER_TEST.toUUID(), QUESTION.name(), "InterviewsC");

        Category categoryD = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE.name(), "InterviewsD");
        Category categoryE = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE.name(), "InterviewsE");
        Category categoryF = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE.name(), "InterviewsF");
        Category categoryG = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE.name(), "InterviewsG");


        Assertions.assertNotNull(categoryA);
        Assertions.assertNotNull(categoryB);
        Assertions.assertNotNull(categoryC);
        Assertions.assertNotNull(categoryC2);

        Assertions.assertNotNull(categoryD);
        Assertions.assertNotNull(categoryE);
        Assertions.assertNotNull(categoryF);
        Assertions.assertNotNull(categoryG);

        Iterable <Category> iterable = categoryService.findCategories(USER_TEST.toUUID(), QUESTIONNAIRE.name());

        List <Category> categories = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(3, categories.size());

        iterable = categoryService.findChildrenCategories(UUID.fromString(categoryC.getUuid()));

        List <Category> categoriesC = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(4, categoriesC.size());

    }

}
