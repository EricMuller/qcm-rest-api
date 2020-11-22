package com.emu.apps.qcm.aggregates;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

	private final Question aQuestion;

	public QuestionTest() {
		this.aQuestion = new Question();
	}
	@Test
	void testType() {
		String param = "123";
		aQuestion.setType(param);
		Object result = aQuestion.getType();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aQuestion.setQuestionText(param);
		Object result = aQuestion.getQuestionText();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		Category param = new Category();
		aQuestion.setCategory(param);
		Object result = aQuestion.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestion.setStatus(param);
		Object result = aQuestion.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aQuestion.setTip(param);
		Object result = aQuestion.getTip();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aQuestion.setUuid(param);
		Object result = aQuestion.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestion.setVersion(param);
		Object result = aQuestion.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestion.setDateCreation(param);
		Object result = aQuestion.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestion.setDateModification(param);
		Object result = aQuestion.getDateModification();
		assertEquals(param, result);
	}

}

