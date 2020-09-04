package com.emu.apps.qcm.api.models;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadTest {

	private Upload aUpload;

	public UploadTest() {
		this.aUpload = new Upload();
	}
	@Test
	void testUuid() {
		String param = "123";
		aUpload.setUuid(param);
		Object result = aUpload.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aUpload.setDateCreation(param);
		Object result = aUpload.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testContentType() {
		String param = "123";
		aUpload.setContentType(param);
		Object result = aUpload.getContentType();
		assertEquals(param, result);
	}

	@Test
	void testFileName() {
		String param = "123";
		aUpload.setFileName(param);
		Object result = aUpload.getFileName();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aUpload.setStatus(param);
		Object result = aUpload.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aUpload.setLibelle(param);
		Object result = aUpload.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aUpload.setType(param);
		Object result = aUpload.getType();
		assertEquals(param, result);
	}

}

