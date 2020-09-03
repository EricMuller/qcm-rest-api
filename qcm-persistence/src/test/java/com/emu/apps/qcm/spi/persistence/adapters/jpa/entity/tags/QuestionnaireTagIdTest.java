package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagIdTest {
private QuestionnaireTagId pojoObject;
public QuestionnaireTagIdTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagId();
}
@Test
void testQuestionnaireId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionnaireId(param);
Object result = pojoObject.getQuestionnaireId();
assertEquals(param, result);
}

@Test
void testTagId() {
Long param = Long.valueOf(123);
pojoObject.setTagId(param);
Object result = pojoObject.getTagId();
assertEquals(param, result);
}

}

