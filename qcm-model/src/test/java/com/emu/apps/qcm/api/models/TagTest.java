package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
private Tag pojoObject;
public TagTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.Tag();
}
@Test
void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

