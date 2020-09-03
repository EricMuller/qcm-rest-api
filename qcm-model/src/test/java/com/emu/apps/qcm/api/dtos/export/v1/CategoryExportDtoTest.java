package com.emu.apps.qcm.api.dtos.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryExportDtoTest {
private CategoryExportDto pojoObject;
public CategoryExportDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.export.v1.CategoryExportDto();
}
@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

@Test
public void testType() {
String param = "123";
pojoObject.setType(param);
Object result = pojoObject.getType();
assertEquals(param, result);
}

}

