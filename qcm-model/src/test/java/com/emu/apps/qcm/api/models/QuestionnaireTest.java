package com.emu.apps.qcm.api.models;

import com.emu.apps.qcm.api.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTest {
private Questionnaire pojoObject;
public QuestionnaireTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.Questionnaire();
}
@Test
void testTitle() {
String param = "123";
pojoObject.setTitle(param);
Object result = pojoObject.getTitle();
assertEquals(param, result);
}

@Test
void testDescription() {
String param = "123";
pojoObject.setDescription(param);
Object result = pojoObject.getDescription();
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
void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
assertEquals(param, result);
}

@Test
void testWebsite() {
String param = "123";
pojoObject.setWebsite(param);
Object result = pojoObject.getWebsite();
assertEquals(param, result);
}

@Test
void testPublished() {
Boolean param = Boolean.valueOf(true);
pojoObject.setPublished(param);
Object result = pojoObject.getPublished();
assertEquals(param, result);
}

@Test
void testCreatedBy() {
String param = "123";
pojoObject.setCreatedBy(param);
Object result = pojoObject.getCreatedBy();
assertEquals(param, result);
}

}

