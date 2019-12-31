package com.emu.apps.qcm.web.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDtoTest {
private CategoryDto pojoObject;
public CategoryDtoTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.web.dtos.CategoryDto();
}
@Test
public void testId() {
String param = "123";
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

