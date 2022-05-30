package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagQuestionEntityTest {

	private final TagQuestionEntity aTagQuestionEntity;

	public TagQuestionEntityTest() {
		this.aTagQuestionEntity = new TagQuestionEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aTagQuestionEntity.setId(param);
		Object result = aTagQuestionEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aTagQuestionEntity.setLibelle(param);
		Object result = aTagQuestionEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aTagQuestionEntity.setDateCreation(param);
		Object result = aTagQuestionEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aTagQuestionEntity.setDateModification(param);
		Object result = aTagQuestionEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aTagQuestionEntity.setUuid(param);
		Object result = aTagQuestionEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aTagQuestionEntity.setVersion(param);
		Object result = aTagQuestionEntity.getVersion();
		assertEquals(param, result);
	}

}

