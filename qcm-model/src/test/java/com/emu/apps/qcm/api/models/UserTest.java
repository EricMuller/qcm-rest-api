package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	private User aUser;

	public UserTest() {
		this.aUser = new User();
	}
	@Test
	void testUuid() {
		String param = "123";
		aUser.setUuid(param);
		Object result = aUser.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testEmail() {
		String param = "123";
		aUser.setEmail(param);
		Object result = aUser.getEmail();
		assertEquals(param, result);
	}

	@Test
	void testUserName() {
		String param = "123";
		aUser.setUserName(param);
		Object result = aUser.getUserName();
		assertEquals(param, result);
	}

	@Test
	void testFirstName() {
		String param = "123";
		aUser.setFirstName(param);
		Object result = aUser.getFirstName();
		assertEquals(param, result);
	}

	@Test
	void testLastName() {
		String param = "123";
		aUser.setLastName(param);
		Object result = aUser.getLastName();
		assertEquals(param, result);
	}

	@Test
	void testCompany() {
		String param = "123";
		aUser.setCompany(param);
		Object result = aUser.getCompany();
		assertEquals(param, result);
	}

}

