package com.emu.apps.qcm.models;

import com.emu.apps.qcm.dtos.FileQuestionDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileQuestionDtoTest {
private FileQuestionDto pojoObject;
public FileQuestionDtoTest() throws Exception {
this.pojoObject = new FileQuestionDto();
}
@Test
public void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
public void testNumber() {
Long param = Long.valueOf(123);
pojoObject.setNumber(param);
Object result = pojoObject.getNumber();
assertEquals(param, result);
}

@Test
public void testQuestion() {
String param = "123";
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

@Test
public void testResponse() {
String param = "123";
pojoObject.setResponse(param);
Object result = pojoObject.getResponse();
assertEquals(param, result);
}

@Test
public void testCategorie() {
String param = "123";
pojoObject.setCategorie(param);
Object result = pojoObject.getCategorie();
assertEquals(param, result);
}

}

