package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionIdTest {
private QuestionnaireQuestionId pojoObject;
public QuestionnaireQuestionIdTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionId();
}
@Test
void testQuestionnaireId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionnaireId(param);
Object result = pojoObject.getQuestionnaireId();
assertEquals(param, result);
}

@Test
void testQuestionId() {
Long param = Long.valueOf(123);
pojoObject.setQuestionId(param);
Object result = pojoObject.getQuestionId();
assertEquals(param, result);
}

}

