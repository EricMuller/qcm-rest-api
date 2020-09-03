package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageDtoTest {
private MessageDto pojoObject;
public MessageDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.MessageDto();
}
@Test
void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
assertEquals(param, result);
}

@Test
void testMessage() {
String param = "123";
pojoObject.setMessage(param);
Object result = pojoObject.getMessage();
assertEquals(param, result);
}

@Test
void testData() {
String param = "123";
pojoObject.setData(param);
Object result = pojoObject.getData();
assertEquals(param, result);
}

}

