package com.emu.apps.qcm.api.models;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadTest {
private Upload pojoObject;
public UploadTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.Upload();
}
@Test
void testUuid() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
void testDateCreation() {
ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
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
void testFileName() {
String param = "123";
pojoObject.setFileName(param);
Object result = pojoObject.getFileName();
assertEquals(param, result);
}

@Test
void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
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
void testType() {
String param = "123";
pojoObject.setType(param);
Object result = pojoObject.getType();
assertEquals(param, result);
}

}

