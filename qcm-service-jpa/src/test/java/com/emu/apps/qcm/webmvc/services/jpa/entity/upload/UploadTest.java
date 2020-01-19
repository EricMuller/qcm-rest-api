package com.emu.apps.qcm.webmvc.services.jpa.entity.upload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadTest {
private Upload pojoObject;
public UploadTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.webmvc.services.jpa.entity.upload.Upload();
}
@Test
public void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
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
public void testPathfileName() {
String param = "123";
pojoObject.setPathfileName(param);
Object result = pojoObject.getPathfileName();
assertEquals(param, result);
}

}

