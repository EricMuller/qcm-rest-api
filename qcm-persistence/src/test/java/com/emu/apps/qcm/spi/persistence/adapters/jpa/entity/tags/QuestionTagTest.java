package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTagTest {
    private QuestionTagEntity pojoObject;

    public QuestionTagTest() throws Exception {
        this.pojoObject = new QuestionTagEntity();
    }

    @Test
    public void testId() {
        QuestionTagId param = new QuestionTagId();
        pojoObject.setId(param);
        Object result = pojoObject.getId();
        assertEquals(param, result);
    }

    @Test
    public void testQuestion() {
        QuestionEntity param = new QuestionEntity();
        pojoObject.setQuestion(param);
        Object result = pojoObject.getQuestion();
        assertEquals(param, result);
    }

    @Test
    public void testTag() {
        Tag param = new Tag();
        pojoObject.setTag(param);
        Object result = pojoObject.getTag();
        assertEquals(param, result);
    }

}

