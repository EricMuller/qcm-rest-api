package com.emu.apps.qcm.services.entity.questionnaires;

import com.emu.apps.qcm.services.entity.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireTest {
private Questionnaire pojoObject;
public QuestionnaireTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.services.entity.questionnaires.Questionnaire();
}
@Test
public void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
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
public void testTitle() {
String param = "123";
pojoObject.setTitle(param);
Object result = pojoObject.getTitle();
assertEquals(param, result);
}

@Test
public void testLocale() {
String param = "123";
pojoObject.setLocale(param);
Object result = pojoObject.getLocale();
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

