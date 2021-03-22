package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.infra.infrastructure.DbFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.shared.exceptions.FunctionnalException;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryBusinessPort;

    @Autowired
   private DbFixture dbFixture;

    @BeforeEach
    void setUp() {
        setPrincipal(USER_TEST.toUUID());
    }

    @Test
    void testGetCategories() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Iterable <Category> categories = categoryBusinessPort.getCategories(USER_TEST, QUESTION.name());

        Assertions.assertTrue(IterableUtils.isEmpty(categories));

    }

    @Test
    void testSaveCategory() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testSaveCategory");
        category.setType(QUESTIONNAIRE.name());

        Category saveCategory = categoryBusinessPort.saveCategory(category, USER_TEST);

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

        category = categoryBusinessPort.saveCategory(category, USER_TEST);
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getUuid());

        Category categoryByUuid = categoryBusinessPort.getCategoryByUuid(category.getUuid());

        Assertions.assertNotNull(categoryByUuid);
        Assertions.assertNotNull(categoryByUuid.getUuid());
        Assertions.assertEquals(USER_TEST.toUUID(), categoryByUuid.getUserId());

    }

}
