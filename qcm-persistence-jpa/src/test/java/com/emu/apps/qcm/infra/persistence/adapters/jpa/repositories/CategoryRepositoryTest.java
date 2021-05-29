package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;


import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import org.apache.commons.collections4.IterableUtils;
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
import java.util.Optional;
import java.util.UUID;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static java.util.UUID.fromString;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

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

    @Autowired
    private CategoryPersistencePort categoryPersistencePort;

    @Autowired
    private DbFixture dbFixture;

    @Test
    @Transactional
    public void findOrCreateByLibelle() {


        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        Category categoryA = categoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsA");
        Category categoryB = categoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsB");
        Category categoryC = categoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsC");

        Category categoryC2 = categoryPersistencePort.findOrCreateByLibelle(principal, QUESTION.name(), "InterviewsC");

        Category categoryD = categoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsD");
        Category categoryE = categoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsE");
        Category categoryF = categoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsF");
        Category categoryG = categoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsG");


        Assertions.assertNotNull(categoryA);
        Assertions.assertNotNull(categoryB);
        Assertions.assertNotNull(categoryC);
        Assertions.assertNotNull(categoryC2);

        Assertions.assertNotNull(categoryD);
        Assertions.assertNotNull(categoryE);
        Assertions.assertNotNull(categoryF);
        Assertions.assertNotNull(categoryG);

        Iterable <Category> iterable = categoryPersistencePort.findCategories(principal, QUESTIONNAIRE.name());

        List <Category> categories = stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(3, categories.size());

        iterable = categoryPersistencePort.findChildrenCategories(fromString(categoryC.getId().toUuid()));

        List <Category> categoriesC = stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(4, categoriesC.size());

    }

    @Test
    void testGetCategories() {


        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        Iterable <Category> categories = categoryPersistencePort.findCategories(principal, QUESTION.name());

        Assertions.assertTrue(IterableUtils.isEmpty(categories));

    }

    @Test
    void testSaveCategory() {

        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testSaveCategory");
        category.setType(QUESTIONNAIRE.name());
        category.setUserId(principal);

        Category saveCategory = categoryPersistencePort.saveCategory(category);

        Assertions.assertNotNull(saveCategory);
        Assertions.assertNotNull(saveCategory.getId().toUuid());
        Assertions.assertEquals(principal, saveCategory.getUserId());

    }

    @Test
    void testGetCategoryByUuid() {

        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();
        dbFixture.emptyDatabase(principal);

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testGetCategoryByUuid");
        category.setType(QUESTIONNAIRE.name());
        category.setUserId(principal);

        category = categoryPersistencePort.saveCategory(category);
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId().toUuid());

        Optional <Category> categoryByUuid = categoryPersistencePort.findByUuid(category.getId().toUuid());

        Assertions.assertTrue(categoryByUuid.isPresent());
        Assertions.assertNotNull(categoryByUuid.get());
        Assertions.assertEquals(principal, categoryByUuid.get().getUserId());

    }
}
