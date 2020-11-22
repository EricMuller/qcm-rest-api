package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.aggregates.Category;
import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static java.util.stream.Collectors.toList;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
public class CategoryRepositoryTest {

    private static final String USER_TEST = SpringBootJpaTestConfig.USER_TEST;

    @Autowired
    private CategoryPersistencePort categoryService;

    @Autowired
    private DbFixture dbFixture;

    @Test
    @Transactional
    public void findOrCreateByLibelle() {

        dbFixture.emptyDatabase();


        Category categoryA = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsA");
        Category categoryB = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsB");
        Category categoryC = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsC");

        Category categoryC2 = categoryService.findOrCreateByLibelle(USER_TEST, QUESTION, "InterviewsC");

        Category categoryD = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsD");
        Category categoryE = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsE");
        Category categoryF = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsF");
        Category categoryG = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsG");


        Assertions.assertNotNull(categoryA);
        Assertions.assertNotNull(categoryB);
        Assertions.assertNotNull(categoryC);
        Assertions.assertNotNull(categoryC2);

        Assertions.assertNotNull(categoryD);
        Assertions.assertNotNull(categoryE);
        Assertions.assertNotNull(categoryF);
        Assertions.assertNotNull(categoryG);

        Iterable <Category> iterable = categoryService.findCategories(USER_TEST, QUESTIONNAIRE);

        List <Category> categories = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(3, categories.size());

        iterable = categoryService.findChildrenCategories(UUID.fromString(categoryC.getUuid()));

        List <Category> categoriesC = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(4, categoriesC.size());

    }

}
