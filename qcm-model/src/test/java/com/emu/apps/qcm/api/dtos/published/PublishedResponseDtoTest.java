package com.emu.apps.qcm.api.dtos.published;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublishedResponseDtoTest {
private PublishedResponseDto pojoObject;
public PublishedResponseDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.published.PublishedResponseDto();
}
@Test
public void testResponse() {
String param = "123";
pojoObject.setResponse(param);
Object result = pojoObject.getResponse();
assertEquals(param, result);
}

@Test
public void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
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

@Test
public void testNumber() {
Long param = Long.valueOf(123);
pojoObject.setNumber(param);
Object result = pojoObject.getNumber();
assertEquals(param, result);
}

}

