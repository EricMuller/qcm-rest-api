package com.emu.apps.qcm.api.models;

import com.emu.apps.qcm.api.dtos.SuggestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuggestDtoTest {
private SuggestDto pojoObject;
public SuggestDtoTest() throws Exception {
this.pojoObject = new SuggestDto();
}
@Test
public void testFieldName() {
String param = "123";
pojoObject.setFieldName(param);
Object result = pojoObject.getFieldName();
assertEquals(param, result);
}

@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

@Test
public void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

}

