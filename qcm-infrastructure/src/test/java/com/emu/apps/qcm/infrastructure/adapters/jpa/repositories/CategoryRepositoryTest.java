package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.infrastructure.ports.CategoryPersistencePort;
import com.emu.apps.qcm.domain.dtos.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type.QUESTION;
import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type.QUESTIONNAIRE;
import static java.util.stream.Collectors.toList;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class CategoryRepositoryTest {

    private static final String USER_TEST = SpringBootTestConfig.USER_TEST;

    @Autowired
    private CategoryPersistencePort categoryService;

    @Test
    @Transactional
    public void findOrCreateByLibelle() throws FunctionnalException {


        CategoryDto categoryA = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsA");
        CategoryDto categoryB = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsB");
        CategoryDto categoryC = categoryService.findOrCreateByLibelle(USER_TEST, QUESTIONNAIRE, "InterviewsC");

        CategoryDto categoryC2 = categoryService.findOrCreateByLibelle(USER_TEST, QUESTION, "InterviewsC");

        CategoryDto categoryD = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsD");
        CategoryDto categoryE = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsE");
        CategoryDto categoryF = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsF");
        CategoryDto categoryG = categoryService.findOrCreateChildByLibelle(UUID.fromString(categoryC.getUuid()), QUESTIONNAIRE, "InterviewsG");


        Assertions.assertNotNull(categoryA);
        Assertions.assertNotNull(categoryB);
        Assertions.assertNotNull(categoryC);
        Assertions.assertNotNull(categoryC2);

        Assertions.assertNotNull(categoryD);
        Assertions.assertNotNull(categoryE);
        Assertions.assertNotNull(categoryF);
        Assertions.assertNotNull(categoryG);

        Iterable <CategoryDto> iterable = categoryService.findCategories(USER_TEST, QUESTIONNAIRE);

        List <CategoryDto> categories = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(3, categories.size());

        iterable = categoryService.findChildrenCategories(UUID.fromString(categoryC.getUuid()));

        List <CategoryDto> categoriesC = StreamSupport.stream(iterable.spliterator(), false).collect(toList());

        Assertions.assertEquals(4, categoriesC.size());

    }

}
