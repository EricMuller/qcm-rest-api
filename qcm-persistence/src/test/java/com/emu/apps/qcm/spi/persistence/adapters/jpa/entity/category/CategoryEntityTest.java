package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {

	private CategoryEntity aCategoryEntity;

	public CategoryEntityTest() {
		this.aCategoryEntity = new CategoryEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aCategoryEntity.setId(param);
		Object result = aCategoryEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aCategoryEntity.setLibelle(param);
		Object result = aCategoryEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testUserId() {
		String param = "123";
		aCategoryEntity.setUserId(param);
		Object result = aCategoryEntity.getUserId();
		assertEquals(param, result);
	}

	@Test
	void testLft() {
		Long param = Long.valueOf(123);
		aCategoryEntity.setLft(param);
		Object result = aCategoryEntity.getLft();
		assertEquals(param, result);
	}

	@Test
	void testRgt() {
		Long param = Long.valueOf(123);
		aCategoryEntity.setRgt(param);
		Object result = aCategoryEntity.getRgt();
		assertEquals(param, result);
	}

	@Test
	void testLevel() {
		Integer param = Integer.valueOf(123);
		aCategoryEntity.setLevel(param);
		Object result = aCategoryEntity.getLevel();
		assertEquals(param, result);
	}

}

