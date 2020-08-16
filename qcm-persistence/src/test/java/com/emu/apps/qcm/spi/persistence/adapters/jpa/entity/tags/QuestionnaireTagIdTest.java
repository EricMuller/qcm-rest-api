package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionnaireTagIdTest {
    private QuestionnaireTagId pojoObject;

    public QuestionnaireTagIdTest() throws Exception {
        this.pojoObject = new QuestionnaireTagId();
    }

    @Test
    public void testQuestionnaireId() {
        Long param = Long.valueOf(123);
        pojoObject.setQuestionnaireId(param);
        Object result = pojoObject.getQuestionnaireId();
        assertEquals(param, result);
    }

    @Test
    public void testTagId() {
        Long param = Long.valueOf(123);
        pojoObject.setTagId(param);
        Object result = pojoObject.getTagId();
        assertEquals(param, result);
    }

}

