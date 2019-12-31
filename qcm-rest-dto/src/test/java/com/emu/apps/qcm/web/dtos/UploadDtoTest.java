package com.emu.apps.qcm.web.dtos;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadDtoTest {
private UploadDto pojoObject;
public UploadDtoTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.web.dtos.UploadDto();
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
public void testDateCreation() {
LocalDateTime param = LocalDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
assertEquals(param, result);
}

}

