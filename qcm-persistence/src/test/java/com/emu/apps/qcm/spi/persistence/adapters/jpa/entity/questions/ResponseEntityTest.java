package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseEntityTest {
private ResponseEntity pojoObject;
public ResponseEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testResponse() {
String param = "123";
pojoObject.setResponse(param);
Object result = pojoObject.getResponse();
assertEquals(param, result);
}

@Test
void testNumber() {
Long param = Long.valueOf(123);
pojoObject.setNumber(param);
Object result = pojoObject.getNumber();
assertEquals(param, result);
}

@Test
void testGood() {
Boolean param = Boolean.valueOf(true);
pojoObject.setGood(param);
Object result = pojoObject.getGood();
assertEquals(param, result);
}

@Test
void testQuestion() {
QuestionEntity param = new QuestionEntity();
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

}

