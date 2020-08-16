package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDtoTest {
private Question pojoObject;
public QuestionDtoTest() throws Exception {
this.pojoObject = new Question();
}
@Test
public void testQuestion() {
String param = "123";
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

}

