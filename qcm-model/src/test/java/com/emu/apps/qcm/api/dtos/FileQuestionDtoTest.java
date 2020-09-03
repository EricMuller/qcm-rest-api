package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileQuestionDtoTest {
private FileQuestionDto pojoObject;
public FileQuestionDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.FileQuestionDto();
}
@Test
void testNumber() {
Long param = Long.valueOf(123);
pojoObject.setNumber(param);
Object result = pojoObject.getNumber();
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
void testResponse() {
String param = "123";
pojoObject.setResponse(param);
Object result = pojoObject.getResponse();
assertEquals(param, result);
}

@Test
void testCategorie() {
String param = "123";
pojoObject.setCategorie(param);
Object result = pojoObject.getCategorie();
assertEquals(param, result);
}

}

