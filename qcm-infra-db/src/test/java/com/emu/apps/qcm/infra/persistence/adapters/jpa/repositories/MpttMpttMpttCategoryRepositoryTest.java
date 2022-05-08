package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;


import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.infra.persistence.MpptCategoryPersistencePort;
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

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType.QUESTIONNAIRE;
import static java.util.UUID.fromString;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {MpttMpttMpttCategoryRepositoryTest.Initializer.class})
class MpttMpttMpttCategoryRepositoryTest {

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
    private MpptCategoryPersistencePort mpptCategoryPersistencePort;

    @Autowired
    private DbFixture dbFixture;

    @Test
    @Transactional
    void findOrCreateByLibelle() {


        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        MpttCategory categoryA = mpptCategoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsA");
        MpttCategory categoryB = mpptCategoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsB");
        MpttCategory categoryC = mpptCategoryPersistencePort.findOrCreateByLibelle(principal, QUESTIONNAIRE.name(), "InterviewsC");

        MpttCategory categoryC2 = mpptCategoryPersistencePort.findOrCreateByLibelle(principal, QUESTION.name(), "InterviewsC");

        MpttCategory categoryD = mpptCategoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsD");
        MpttCategory categoryE = mpptCategoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsE");
        MpttCategory categoryF = mpptCategoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsF");
        MpttCategory categoryG = mpptCategoryPersistencePort.findOrCreateChildByLibelle(fromString(categoryC.getId().toUuid()), QUESTIONNAIRE.name(), "InterviewsG");


        Assertions.assertNotNull(categoryA);
        Assertions.assertNotNull(categoryB);
        Assertions.assertNotNull(categoryC);
        Assertions.assertNotNull(categoryC2);

        Assertions.assertNotNull(categoryD);
        Assertions.assertNotNull(categoryE);
        Assertions.assertNotNull(categoryF);
        Assertions.assertNotNull(categoryG);

        Iterable <MpttCategory> iterable = mpptCategoryPersistencePort.findCategories(principal, QUESTIONNAIRE.name());

        List <MpttCategory> categories = stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(3, categories.size());

        iterable = mpptCategoryPersistencePort.findChildrenCategories(fromString(categoryC.getId().toUuid()));

        List <MpttCategory> categoriesC = stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(4, categoriesC.size());

    }

    @Test
    void testGetCategories() {


        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        Iterable <MpttCategory> categories = mpptCategoryPersistencePort.findCategories(principal, QUESTION.name());

        Assertions.assertTrue(IterableUtils.isEmpty(categories));

    }

    @Test
    void testSaveCategory() {

        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();

        dbFixture.emptyDatabase(principal);

        MpttCategory mpttCategory = new MpttCategory();
        mpttCategory.setLibelle("CategoryBusinessPortTest.testSaveCategory");
        mpttCategory.setType(QUESTIONNAIRE.name());
        mpttCategory.setUserId(principal);

        MpttCategory saveMpttCategory = mpptCategoryPersistencePort.saveCategory(mpttCategory);

        Assertions.assertNotNull(saveMpttCategory);
        Assertions.assertNotNull(saveMpttCategory.getId().toUuid());
        Assertions.assertEquals(principal, saveMpttCategory.getUserId());

    }

    @Test
    void testGetCategoryByUuid() {

        final String principal = getClass().getSimpleName() + "." + UUID.randomUUID();
        dbFixture.emptyDatabase(principal);

        MpttCategory mpttCategory = new MpttCategory();
        mpttCategory.setLibelle("CategoryBusinessPortTest.testGetCategoryByUuid");
        mpttCategory.setType(QUESTIONNAIRE.name());
        mpttCategory.setUserId(principal);

        mpttCategory = mpptCategoryPersistencePort.saveCategory(mpttCategory);
        Assertions.assertNotNull(mpttCategory);
        Assertions.assertNotNull(mpttCategory.getId().toUuid());

        Optional <MpttCategory> categoryByUuid = mpptCategoryPersistencePort.findByUuid(mpttCategory.getId().toUuid());

        Assertions.assertTrue(categoryByUuid.isPresent());
        Assertions.assertNotNull(categoryByUuid.get());
        Assertions.assertEquals(principal, categoryByUuid.get().getUserId());

    }
}
