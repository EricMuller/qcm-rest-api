package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadEntityTest {

	private UploadEntity aUploadEntity;

	public UploadEntityTest() {
		this.aUploadEntity = new UploadEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aUploadEntity.setId(param);
		Object result = aUploadEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testFileName() {
		String param = "123";
		aUploadEntity.setFileName(param);
		Object result = aUploadEntity.getFileName();
		assertEquals(param, result);
	}

	@Test
	void testPathfileName() {
		String param = "123";
		aUploadEntity.setPathfileName(param);
		Object result = aUploadEntity.getPathfileName();
		assertEquals(param, result);
	}

	@Test
	void testContentType() {
		String param = "123";
		aUploadEntity.setContentType(param);
		Object result = aUploadEntity.getContentType();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aUploadEntity.setLibelle(param);
		Object result = aUploadEntity.getLibelle();
		assertEquals(param, result);
	}

}

