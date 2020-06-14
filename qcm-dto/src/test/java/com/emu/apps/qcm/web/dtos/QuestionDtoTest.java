package com.emu.apps.qcm.web.dtos;

import com.emu.apps.qcm.domain.dtos.QuestionDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDtoTest {
private QuestionDto pojoObject;
public QuestionDtoTest() throws Exception {
this.pojoObject = new QuestionDto();
}
@Test
public void testQuestion() {
String param = "123";
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

}

