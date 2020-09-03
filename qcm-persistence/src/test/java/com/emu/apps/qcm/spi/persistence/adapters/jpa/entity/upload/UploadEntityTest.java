package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadEntityTest {
private UploadEntity pojoObject;
public UploadEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.UploadEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testFileName() {
String param = "123";
pojoObject.setFileName(param);
Object result = pojoObject.getFileName();
assertEquals(param, result);
}

@Test
void testPathfileName() {
String param = "123";
pojoObject.setPathfileName(param);
Object result = pojoObject.getPathfileName();
assertEquals(param, result);
}

@Test
void testContentType() {
String param = "123";
pojoObject.setContentType(param);
Object result = pojoObject.getContentType();
assertEquals(param, result);
}

@Test
void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

