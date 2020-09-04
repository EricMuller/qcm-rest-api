package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

	private Tag aTag;

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

}

