package com.emu.apps.qcm.models.question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionLightDtoTest {
    private QuestionLightDto pojoObject;

    public QuestionLightDtoTest() {
        this.pojoObject = new com.emu.apps.qcm.models.question.QuestionLightDto();
    }

    @Test
    public void testQuestion() {
        String param = "123";
        pojoObject.setQuestion(param);
        Object result = pojoObject.getQuestion();
        assertEquals(param, result);
    }

}

