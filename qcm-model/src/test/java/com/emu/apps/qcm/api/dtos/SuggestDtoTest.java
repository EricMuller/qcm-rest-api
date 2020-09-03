package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuggestDtoTest {
private SuggestDto pojoObject;
public SuggestDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.SuggestDto();
}
@Test
void testFieldName() {
String param = "123";
pojoObject.setFieldName(param);
Object result = pojoObject.getFieldName();
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
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

}

