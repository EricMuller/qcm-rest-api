package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookTest {
private WebHook pojoObject;
public WebHookTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.WebHook();
}
@Test
void testUrl() {
String param = "123";
pojoObject.setUrl(param);
Object result = pojoObject.getUrl();
assertEquals(param, result);
}

@Test
void testSecret() {
String param = "123";
pojoObject.setSecret(param);
Object result = pojoObject.getSecret();
assertEquals(param, result);
}

@Test
void testDefaultTimeOut() {
Long param = Long.valueOf(123);
pojoObject.setDefaultTimeOut(param);
Object result = pojoObject.getDefaultTimeOut();
assertEquals(param, result);
}

}

