package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionnaireTest {
    private QuestionnaireEntity pojoObject;

    public QuestionnaireTest() throws Exception {
        this.pojoObject = new QuestionnaireEntity();
    }

    @Test
    public void testId() {
        Long param = Long.valueOf(123);
        pojoObject.setId(param);
        Object result = pojoObject.getId();
        assertEquals(param, result);
    }

    @Test
    public void testDescription() {
        String param = "123";
        pojoObject.setDescription(param);
        Object result = pojoObject.getDescription();
        assertEquals(param, result);
    }

    @Test
    public void testTitle() {
        String param = "123";
        pojoObject.setTitle(param);
        Object result = pojoObject.getTitle();
        assertEquals(param, result);
    }

    @Test
    public void testLocale() {
        String param = "123";
        pojoObject.setLocale(param);
        Object result = pojoObject.getLocale();
        assertEquals(param, result);
    }

    @Test
    public void testCategory() {
        CategoryEntity param = new CategoryEntity();
        pojoObject.setCategory(param);
        Object result = pojoObject.getCategory();
        assertEquals(param, result);
    }

}

