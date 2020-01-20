package com.emu.apps.qcm.services.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTagIdTest {
private QuestionTagId pojoObject;
public QuestionTagIdTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.services.entity.tags.QuestionTagId();
}
@Test
public void testQuestionId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionId(param);
Object result = pojoObject.getQuestionId();
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

