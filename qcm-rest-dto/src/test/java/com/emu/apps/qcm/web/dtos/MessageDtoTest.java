package com.emu.apps.qcm.web.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDtoTest {
private MessageDto pojoObject;
public MessageDtoTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.web.dtos.MessageDto();
}
@Test
public void testMessage() {
String param = "123";
pojoObject.setMessage(param);
Object result = pojoObject.getMessage();
assertEquals(param, result);
}

}

