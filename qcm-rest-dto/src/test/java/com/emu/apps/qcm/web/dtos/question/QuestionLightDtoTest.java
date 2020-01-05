package com.emu.apps.qcm.web.dtos.question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionLightDtoTest {
private QuestionLightDto pojoObject;
public QuestionLightDtoTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.web.dtos.question.QuestionLightDto();
}
@Test
public void testQuestion() {
String param = "123";
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

}

