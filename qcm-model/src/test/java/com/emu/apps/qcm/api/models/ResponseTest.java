package com.emu.apps.qcm.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

	private final Response aResponse;

	public ResponseTest() {
		this.aResponse = new Response();
	}
	@Test
	void testUuid() {
		String param = "123";
		aResponse.setUuid(param);
		Object result = aResponse.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aResponse.setLibelle(param);
		Object result = aResponse.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testGood() {
		Boolean param = Boolean.TRUE;
		aResponse.setGood(param);
		Object result = aResponse.getGood();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aResponse.setVersion(param);
		Object result = aResponse.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testNumber() {
		Long param = Long.valueOf(123);
		aResponse.setNumber(param);
		Object result = aResponse.getNumber();
		assertEquals(param, result);
	}

}

