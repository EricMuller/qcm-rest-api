package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
private User pojoObject;
public UserTest() {
this.pojoObject = new com.emu.apps.qcm.api.models.User();
}
@Test
void testUuid() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
void testEmail() {
String param = "123";
pojoObject.setEmail(param);
Object result = pojoObject.getEmail();
assertEquals(param, result);
}

@Test
void testUserName() {
String param = "123";
pojoObject.setUserName(param);
Object result = pojoObject.getUserName();
assertEquals(param, result);
}

@Test
void testFirstName() {
String param = "123";
pojoObject.setFirstName(param);
Object result = pojoObject.getFirstName();
assertEquals(param, result);
}

@Test
void testLastName() {
String param = "123";
pojoObject.setLastName(param);
Object result = pojoObject.getLastName();
assertEquals(param, result);
}

@Test
void testCompany() {
String param = "123";
pojoObject.setCompany(param);
Object result = pojoObject.getCompany();
assertEquals(param, result);
}

}

