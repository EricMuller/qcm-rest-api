package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionnaireTagTest {
    private QuestionnaireTagEntity pojoObject;

    public QuestionnaireTagTest() throws Exception {
        this.pojoObject = new QuestionnaireTagEntity();
    }

    @Test
    public void testId() {
        QuestionnaireTagId param = new QuestionnaireTagId();
        pojoObject.setId(param);
        Object result = pojoObject.getId();
        assertEquals(param, result);
    }

    @Test
    public void testQuestionnaire() {
        QuestionnaireEntity param = new QuestionnaireEntity();
        pojoObject.setQuestionnaire(param);
        Object result = pojoObject.getQuestionnaire();
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

