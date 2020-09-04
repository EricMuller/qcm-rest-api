package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload;

import java.util.UUID;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadEntityTest {

	private final UploadEntity aUploadEntity;

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

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aUploadEntity.setUuid(param);
		Object result = aUploadEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aUploadEntity.setDateCreation(param);
		Object result = aUploadEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aUploadEntity.setDateModification(param);
		Object result = aUploadEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aUploadEntity.setVersion(param);
		Object result = aUploadEntity.getVersion();
		assertEquals(param, result);
	}

}

