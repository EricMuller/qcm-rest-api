package com.emu.apps.qcm.api.dtos.published;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushishedQuestionnaireQuestionDtoTest {

	private PushishedQuestionnaireQuestionDto aPushishedQuestionnaireQuestionDto;

	public PushishedQuestionnaireQuestionDtoTest() {
		this.aPushishedQuestionnaireQuestionDto = new PushishedQuestionnaireQuestionDto();
	}
	@Test
	void testQuestionnaireUuid() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setQuestionnaireUuid(param);
		Object result = aPushishedQuestionnaireQuestionDto.getQuestionnaireUuid();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setLibelle(param);
		Object result = aPushishedQuestionnaireQuestionDto.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setType(param);
		Object result = aPushishedQuestionnaireQuestionDto.getType();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setStatus(param);
		Object result = aPushishedQuestionnaireQuestionDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setCategory(param);
		Object result = aPushishedQuestionnaireQuestionDto.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aPushishedQuestionnaireQuestionDto.setTip(param);
		Object result = aPushishedQuestionnaireQuestionDto.getTip();
		assertEquals(param, result);
	}

	@Test
	void testPosition() {
		Long param = Long.valueOf(123);
		aPushishedQuestionnaireQuestionDto.setPosition(param);
		Object result = aPushishedQuestionnaireQuestionDto.getPosition();
		assertEquals(param, result);
	}

}

