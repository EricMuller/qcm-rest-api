package com.emu.apps.qcm.web.dtos;

import com.emu.apps.qcm.domain.dtos.CategoryDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDtoTest {
private CategoryDto pojoObject;
public CategoryDtoTest() throws Exception {
this.pojoObject = new CategoryDto();
}


@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

