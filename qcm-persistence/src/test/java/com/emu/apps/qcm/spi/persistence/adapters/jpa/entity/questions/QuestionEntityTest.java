package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionEntityTest {
private QuestionEntity pojoObject;
public QuestionEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testMandatory() {
Boolean param = Boolean.valueOf(true);
pojoObject.setMandatory(param);
Object result = pojoObject.getMandatory();
assertEquals(param, result);
}

@Test
void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

@Test
void testCategory() {
CategoryEntity param = new CategoryEntity();
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

@Test
void testTip() {
String param = "123";
pojoObject.setTip(param);
Object result = pojoObject.getTip();
assertEquals(param, result);
}

}

