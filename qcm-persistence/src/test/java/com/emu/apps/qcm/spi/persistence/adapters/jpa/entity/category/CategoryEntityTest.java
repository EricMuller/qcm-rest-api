package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {
private CategoryEntity pojoObject;
public CategoryEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
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
void testUserId() {
String param = "123";
pojoObject.setUserId(param);
Object result = pojoObject.getUserId();
assertEquals(param, result);
}

@Test
void testLft() {
Long param = Long.valueOf(123);
pojoObject.setLft(param);
Object result = pojoObject.getLft();
assertEquals(param, result);
}

@Test
void testRgt() {
Long param = Long.valueOf(123);
pojoObject.setRgt(param);
Object result = pojoObject.getRgt();
assertEquals(param, result);
}

@Test
void testLevel() {
Integer param = Integer.valueOf(123);
pojoObject.setLevel(param);
Object result = pojoObject.getLevel();
assertEquals(param, result);
}

}

