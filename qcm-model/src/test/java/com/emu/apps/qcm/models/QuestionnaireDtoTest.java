package com.emu.apps.qcm.models;

import com.emu.apps.qcm.models.CategoryDto;
import com.emu.apps.qcm.models.QuestionnaireDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireDtoTest {
private QuestionnaireDto pojoObject;
public QuestionnaireDtoTest() throws Exception {
this.pojoObject = new QuestionnaireDto();
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
CategoryDto param = new CategoryDto();
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

}

