package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTagDtoTest {
private QuestionTag pojoObject;
public QuestionTagDtoTest() throws Exception {
this.pojoObject = new QuestionTag();
}
@Test
public void testId() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

