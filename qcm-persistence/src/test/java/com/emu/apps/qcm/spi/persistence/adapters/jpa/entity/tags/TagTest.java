package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import java.util.UUID;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

	private final Tag aTag;

	public TagTest() {
		this.aTag = new Tag();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aTag.setId(param);
		Object result = aTag.getId();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aTag.setLibelle(param);
		Object result = aTag.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aTag.setUuid(param);
		Object result = aTag.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aTag.setDateCreation(param);
		Object result = aTag.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aTag.setDateModification(param);
		Object result = aTag.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aTag.setVersion(param);
		Object result = aTag.getVersion();
		assertEquals(param, result);
	}

}

