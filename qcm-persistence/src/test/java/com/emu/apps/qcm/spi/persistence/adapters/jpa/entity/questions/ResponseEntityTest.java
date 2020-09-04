package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseEntityTest {

	private ResponseEntity aResponseEntity;

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
	void testLibelle() {
		String param = "123";
		aResponseEntity.setLibelle(param);
		Object result = aResponseEntity.getLibelle();
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
		Boolean param = Boolean.valueOf(true);
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

}

