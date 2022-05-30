package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagQuestionnaireEntityTest {

	private final TagQuestionnaireEntity aTagQuestionnaireEntity;

	public TagQuestionnaireEntityTest() {
		this.aTagQuestionnaireEntity = new TagQuestionnaireEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aTagQuestionnaireEntity.setId(param);
		Object result = aTagQuestionnaireEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aTagQuestionnaireEntity.setLibelle(param);
		Object result = aTagQuestionnaireEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aTagQuestionnaireEntity.setDateCreation(param);
		Object result = aTagQuestionnaireEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aTagQuestionnaireEntity.setDateModification(param);
		Object result = aTagQuestionnaireEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aTagQuestionnaireEntity.setUuid(param);
		Object result = aTagQuestionnaireEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aTagQuestionnaireEntity.setVersion(param);
		Object result = aTagQuestionnaireEntity.getVersion();
		assertEquals(param, result);
	}

}

