package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireDtoTest {
private Questionnaire pojoObject;
public QuestionnaireDtoTest() throws Exception {
this.pojoObject = new Questionnaire();
}
@Test
public void testTitle() {
String param = "123";
pojoObject.setTitle(param);
Object result = pojoObject.getTitle();
assertEquals(param, result);
}

@Test
public void testDescription() {
String param = "123";
pojoObject.setDescription(param);
Object result = pojoObject.getDescription();
assertEquals(param, result);
}

@Test
public void testCategory() {
Category param = new Category();
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

}

