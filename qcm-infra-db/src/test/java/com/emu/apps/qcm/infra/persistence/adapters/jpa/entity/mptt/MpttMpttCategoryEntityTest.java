package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MpttMpttCategoryEntityTest {

	private final MpttCategoryEntity aMpttCategoryEntity;

	public MpttMpttCategoryEntityTest() {
		this.aMpttCategoryEntity = new MpttCategoryEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aMpttCategoryEntity.setId(param);
		Object result = aMpttCategoryEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aMpttCategoryEntity.setLibelle(param);
		Object result = aMpttCategoryEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testUserId() {
		String param = "123";
		aMpttCategoryEntity.setUserId(param);
		Object result = aMpttCategoryEntity.getUserId();
		assertEquals(param, result);
	}

	@Test
	void testLft() {
		Long param = Long.valueOf(123);
		aMpttCategoryEntity.setLft(param);
		Object result = aMpttCategoryEntity.getLft();
		assertEquals(param, result);
	}

	@Test
	void testRgt() {
		Long param = Long.valueOf(123);
		aMpttCategoryEntity.setRgt(param);
		Object result = aMpttCategoryEntity.getRgt();
		assertEquals(param, result);
	}

	@Test
	void testLevel() {
		Integer param = Integer.valueOf(123);
		aMpttCategoryEntity.setLevel(param);
		Object result = aMpttCategoryEntity.getLevel();
		assertEquals(param, result);
	}



	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aMpttCategoryEntity.setDateCreation(param);
		Object result = aMpttCategoryEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aMpttCategoryEntity.setDateModification(param);
		Object result = aMpttCategoryEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aMpttCategoryEntity.setVersion(param);
		Object result = aMpttCategoryEntity.getVersion();
		assertEquals(param, result);
	}

}

