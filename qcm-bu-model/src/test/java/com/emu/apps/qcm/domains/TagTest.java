package com.emu.apps.qcm.domains;

import java.time.ZonedDateTime;

import com.emu.apps.qcm.domain.models.tag.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

	private final Tag aTag;

	public TagTest() {
		this.aTag = new Tag();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aTag.setLibelle(param);
		Object result = aTag.getLibelle();
		assertEquals(param, result);
	}



	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aTag.setVersion(param);
		Object result = aTag.getVersion();
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

}

