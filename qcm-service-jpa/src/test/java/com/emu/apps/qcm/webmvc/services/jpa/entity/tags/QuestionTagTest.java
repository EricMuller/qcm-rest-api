package com.emu.apps.qcm.webmvc.services.jpa.entity.tags;

import com.emu.apps.qcm.webmvc.services.jpa.entity.questions.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTagTest {
    private QuestionTag pojoObject;

    public QuestionTagTest() throws Exception {
        this.pojoObject = new com.emu.apps.qcm.webmvc.services.jpa.entity.tags.QuestionTag();
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
        Question param = new Question();
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

