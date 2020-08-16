package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {
    private QuestionEntity pojoObject;

    public QuestionTest() throws Exception {
        this.pojoObject = new QuestionEntity();
    }

    @Test
    public void testId() {
        Long param = Long.valueOf(123);
        pojoObject.setId(param);
        Object result = pojoObject.getId();
        assertEquals(param, result);
    }

    @Test
    public void testMandatory() {
        Boolean param = Boolean.valueOf(true);
        pojoObject.setMandatory(param);
        Object result = pojoObject.getMandatory();
        assertEquals(param, result);
    }

    @Test
    public void testQuestion() {
        String param = "123";
        pojoObject.setQuestion(param);
        Object result = pojoObject.getQuestion();
        assertEquals(param, result);
    }

}

