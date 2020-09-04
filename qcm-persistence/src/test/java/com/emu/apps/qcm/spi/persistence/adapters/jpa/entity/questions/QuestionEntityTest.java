package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import java.util.UUID;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionEntityTest {

	private final QuestionEntity aQuestionEntity;

	public QuestionEntityTest() {
		this.aQuestionEntity = new QuestionEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aQuestionEntity.setId(param);
		Object result = aQuestionEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testMandatory() {
		Boolean param = Boolean.TRUE;
		aQuestionEntity.setMandatory(param);
		Object result = aQuestionEntity.getMandatory();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aQuestionEntity.setLibelle(param);
		Object result = aQuestionEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		CategoryEntity param = new CategoryEntity();
		aQuestionEntity.setCategory(param);
		Object result = aQuestionEntity.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aQuestionEntity.setTip(param);
		Object result = aQuestionEntity.getTip();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aQuestionEntity.setUuid(param);
		Object result = aQuestionEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionEntity.setDateCreation(param);
		Object result = aQuestionEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionEntity.setDateModification(param);
		Object result = aQuestionEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionEntity.setVersion(param);
		Object result = aQuestionEntity.getVersion();
		assertEquals(param, result);
	}

}
