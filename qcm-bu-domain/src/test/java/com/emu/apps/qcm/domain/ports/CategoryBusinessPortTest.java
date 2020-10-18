package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
class CategoryBusinessPortTest {

    @Autowired
    private CategoryBusinessPort categoryBusinessPort;

    @Autowired
   private DbFixture dbFixture;

    @BeforeEach
    void setUp() {
        AuthentificationContextHolder.setUser(SpringBootJpaTestConfig.USER_TEST);
    }

    @Test
    void testGetCategories() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Iterable <Category> categories = categoryBusinessPort.getCategories(SpringBootJpaTestConfig.USER_TEST, Type.QUESTION);

        Assertions.assertTrue(IterableUtils.isEmpty(categories));

    }

    @Test
    void testSaveCategory() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testSaveCategory");
        category.setType(Type.QUESTIONNAIRE.name());

        Category saveCategory = categoryBusinessPort.saveCategory(category, SpringBootJpaTestConfig.USER_TEST);

        Assertions.assertNotNull(saveCategory);
        Assertions.assertNotNull(saveCategory.getUuid());
        Assertions.assertEquals(SpringBootJpaTestConfig.USER_TEST, saveCategory.getUserId());

    }

    @Test
    void testGetCategoryByUuid() throws FunctionnalException {

        dbFixture.emptyDatabase();

        Category category = new Category();
        category.setLibelle("CategoryBusinessPortTest.testGetCategoryByUuid");
        category.setType(Type.QUESTIONNAIRE.name());

        category = categoryBusinessPort.saveCategory(category, SpringBootJpaTestConfig.USER_TEST);
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getUuid());

        Category categoryByUuid = categoryBusinessPort.getCategoryByUuid(category.getUuid());

        Assertions.assertNotNull(categoryByUuid);
        Assertions.assertNotNull(categoryByUuid.getUuid());
        Assertions.assertEquals(SpringBootJpaTestConfig.USER_TEST, categoryByUuid.getUserId());

    }

}
