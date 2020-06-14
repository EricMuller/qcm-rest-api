package com.emu.apps.qcm.web.dtos;

import com.emu.apps.qcm.domain.dtos.UploadDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
LocalDateTime param = LocalDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
assertEquals(param, result);
}

}

