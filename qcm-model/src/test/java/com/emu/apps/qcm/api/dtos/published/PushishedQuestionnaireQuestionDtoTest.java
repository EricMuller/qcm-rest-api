package com.emu.apps.qcm.api.dtos.published;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushishedQuestionnaireQuestionDtoTest {
private PushishedQuestionnaireQuestionDto pojoObject;
public PushishedQuestionnaireQuestionDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.published.PushishedQuestionnaireQuestionDto();
}
@Test
void testQuestionnaireUuid() {
String param = "123";
pojoObject.setQuestionnaireUuid(param);
Object result = pojoObject.getQuestionnaireUuid();
assertEquals(param, result);
}

@Test
void testQuestion() {
String param = "123";
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

@Test
void testType() {
String param = "123";
pojoObject.setType(param);
Object result = pojoObject.getType();
assertEquals(param, result);
}

@Test
void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
assertEquals(param, result);
}

@Test
void testCategory() {
String param = "123";
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

@Test
void testTip() {
String param = "123";
pojoObject.setTip(param);
Object result = pojoObject.getTip();
assertEquals(param, result);
}

@Test
void testPosition() {
Long param = Long.valueOf(123);
pojoObject.setPosition(param);
Object result = pojoObject.getPosition();
assertEquals(param, result);
}

}

