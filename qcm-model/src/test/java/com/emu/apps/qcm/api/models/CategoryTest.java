package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
private Category pojoObject;
public CategoryTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.Category();
}
@Test
void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

@Test
void testType() {
String param = "123";
pojoObject.setType(param);
Object result = pojoObject.getType();
assertEquals(param, result);
}

@Test
void testUserId() {
String param = "123";
pojoObject.setUserId(param);
Object result = pojoObject.getUserId();
assertEquals(param, result);
}

}

