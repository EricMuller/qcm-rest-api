package com.emu.apps.qcm.api.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImportTest {

	private final Import aImport;

	public ImportTest() {
		this.aImport = new Import();
	}
	@Test
	void testUuid() {
		String param = "123";
		aImport.setUuid(param);
		Object result = aImport.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		LocalDateTime param = LocalDateTime.now();
		aImport.setDateCreation(param);
		Object result = aImport.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aImport.setStatus(param);
		Object result = aImport.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aImport.setLibelle(param);
		Object result = aImport.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aImport.setType(param);
		Object result = aImport.getType();
		assertEquals(param, result);
	}

}
