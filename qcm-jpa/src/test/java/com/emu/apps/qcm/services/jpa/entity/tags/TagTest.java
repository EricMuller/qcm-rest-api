package com.emu.apps.qcm.services.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {
private Tag pojoObject;
public TagTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.services.jpa.entity.tags.Tag();
}
@Test
public void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
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

