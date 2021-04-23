package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.shared.exceptions.FunctionnalException;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class CategoryRepositoryTest {

    @Autowired
    private CategoryPersistencePort categoryBusinessPort;

    @Autowired
    private DbFixture dbFixture;

    @BeforeEach
    void setUp() {
        setPrincipal(USER_TEST.toUUID());
    }

    @Test
    void testGetCategories() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Iterable <Category> categories = categoryBusinessPort.findCategories(USER_TEST.toUUID(), QUESTION.name());

        Assertions.assertTrue(IterableUtils.isEmpty(categories));

    }

    @Test
    void testSaveCategory() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testSaveCategory");
        category.setType(QUESTIONNAIRE.name());
        category.setUserId(USER_TEST.toUUID());

        Category saveCategory = categoryBusinessPort.saveCategory(category);

        Assertions.assertNotNull(saveCategory);
        Assertions.assertNotNull(saveCategory.getUuid());
        Assertions.assertEquals(USER_TEST.toUUID(), saveCategory.getUserId());

    }

    @Test
    void testGetCategoryByUuid() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testGetCategoryByUuid");
        category.setType(QUESTIONNAIRE.name());
        category.setUserId(USER_TEST.toUUID());

        category = categoryBusinessPort.saveCategory(category);
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getUuid());

        Optional <Category> categoryByUuid = categoryBusinessPort.findByUuid(category.getUuid());

        Assertions.assertTrue(categoryByUuid.isPresent());
        Assertions.assertNotNull(categoryByUuid.get());
        Assertions.assertEquals(USER_TEST.toUUID(), categoryByUuid.get().getUserId());

    }

}
