package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagIdTest {
private QuestionTagId pojoObject;
public QuestionTagIdTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagId();
}
@Test
void testQuestionId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionId(param);
Object result = pojoObject.getQuestionId();
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

