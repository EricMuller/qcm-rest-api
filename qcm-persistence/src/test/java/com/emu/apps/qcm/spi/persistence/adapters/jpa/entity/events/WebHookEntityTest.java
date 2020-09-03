package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookEntityTest {
private WebHookEntity pojoObject;
public WebHookEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events.WebHookEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testUrl() {
String param = "123";
pojoObject.setUrl(param);
Object result = pojoObject.getUrl();
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
void testSecret() {
String param = "123";
pojoObject.setSecret(param);
Object result = pojoObject.getSecret();
assertEquals(param, result);
}

@Test
void testUser() {
UserEntity param = new UserEntity();
pojoObject.setUser(param);
Object result = pojoObject.getUser();
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

