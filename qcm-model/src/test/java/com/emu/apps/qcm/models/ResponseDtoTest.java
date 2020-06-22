package com.emu.apps.qcm.models;

import com.emu.apps.qcm.models.ResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseDtoTest {
private ResponseDto pojoObject;
public ResponseDtoTest() throws Exception {
this.pojoObject = new ResponseDto();
}
@Test
public void testId() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
public void testResponse() {
String param = "123";
pojoObject.setResponse(param);
Object result = pojoObject.getResponse();
assertEquals(param, result);
}

@Test
public void testGood() {
Boolean param = Boolean.valueOf(true);
pojoObject.setGood(param);
Object result = pojoObject.getGood();
assertEquals(param, result);
}

@Test
public void testVersion() {
Long param = Long.valueOf(123);
pojoObject.setVersion(param);
Object result = pojoObject.getVersion();
assertEquals(param, result);
}

}

