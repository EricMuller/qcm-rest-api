package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagDtoTest {
private Tag pojoObject;
public TagDtoTest() throws Exception {
this.pojoObject = new Tag();
}
@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

