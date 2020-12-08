package com.emu.apps.qcm.domains;

import java.time.ZonedDateTime;

import com.emu.apps.qcm.domain.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

	private final Category aCategory;

	public CategoryTest() {
		this.aCategory = new Category();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aCategory.setLibelle(param);
		Object result = aCategory.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aCategory.setType(param);
		Object result = aCategory.getType();
		assertEquals(param, result);
	}

	@Test
	void testUserId() {
		String param = "123";
		aCategory.setUserId(param);
		Object result = aCategory.getUserId();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aCategory.setUuid(param);
		Object result = aCategory.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aCategory.setVersion(param);
		Object result = aCategory.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aCategory.setDateCreation(param);
		Object result = aCategory.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aCategory.setDateModification(param);
		Object result = aCategory.getDateModification();
		assertEquals(param, result);
	}

}

