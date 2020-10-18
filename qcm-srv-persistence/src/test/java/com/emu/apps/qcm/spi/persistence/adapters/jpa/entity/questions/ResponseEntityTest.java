package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import java.util.UUID;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseEntityTest {

	private final ResponseEntity aResponseEntity;

	public ResponseEntityTest() {
		this.aResponseEntity = new ResponseEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aResponseEntity.setId(param);
		Object result = aResponseEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testResponseText() {
		String param = "123";
		aResponseEntity.setResponseText(param);
		Object result = aResponseEntity.getResponseText();
		assertEquals(param, result);
	}

	@Test
	void testNumber() {
		Long param = Long.valueOf(123);
		aResponseEntity.setNumber(param);
		Object result = aResponseEntity.getNumber();
		assertEquals(param, result);
	}

	@Test
	void testGood() {
		Boolean param = Boolean.TRUE;
		aResponseEntity.setGood(param);
		Object result = aResponseEntity.getGood();
		assertEquals(param, result);
	}

	@Test
	void testQuestion() {
		QuestionEntity param = new QuestionEntity();
		aResponseEntity.setQuestion(param);
		Object result = aResponseEntity.getQuestion();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aResponseEntity.setUuid(param);
		Object result = aResponseEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aResponseEntity.setDateCreation(param);
		Object result = aResponseEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aResponseEntity.setDateModification(param);
		Object result = aResponseEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aResponseEntity.setVersion(param);
		Object result = aResponseEntity.getVersion();
		assertEquals(param, result);
	}

}

