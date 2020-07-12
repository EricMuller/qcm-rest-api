package com.emu.apps.qcm.models;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadDtoTest {
private UploadDto pojoObject;
public UploadDtoTest() throws Exception {
this.pojoObject = new UploadDto();
}
@Test
public void testId() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
public void testFileName() {
String param = "123";
pojoObject.setFileName(param);
Object result = pojoObject.getFileName();
assertEquals(param, result);
}

@Test
public void testDateCreation() {
    ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
assertEquals(param, result);
}

}

