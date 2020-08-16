package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireQuestionIdTest {
private QuestionnaireQuestionId pojoObject;
public QuestionnaireQuestionIdTest() throws Exception {
this.pojoObject = new QuestionnaireQuestionId();
}
@Test
public void testQuestionnaireId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionnaireId(param);
Object result = pojoObject.getQuestionnaireId();
assertEquals(param, result);
}

@Test
public void testQuestionId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionId(param);
Object result = pojoObject.getQuestionId();
assertEquals(param, result);
}

}

