package com.emu.apps.qcm.api.models;

import com.emu.apps.qcm.api.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionTest {
private QuestionnaireQuestion pojoObject;
public QuestionnaireQuestionTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.QuestionnaireQuestion();
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
Category param = new Category();
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
Integer param = Integer.valueOf(123);
pojoObject.setPosition(param);
Object result = pojoObject.getPosition();
assertEquals(param, result);
}

@Test
void testPoints() {
Integer param = Integer.valueOf(123);
pojoObject.setPoints(param);
Object result = pojoObject.getPoints();
assertEquals(param, result);
}

}

