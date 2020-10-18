package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileQuestionDtoTest {

	private final FileQuestionDto aFileQuestionDto;

	public FileQuestionDtoTest() {
		this.aFileQuestionDto = new FileQuestionDto();
	}
	@Test
	void testNumber() {
		Long param = Long.valueOf(123);
		aFileQuestionDto.setNumber(param);
		Object result = aFileQuestionDto.getNumber();
		assertEquals(param, result);
	}

	@Test
	void testQuestion() {
		String param = "123";
		aFileQuestionDto.setQuestion(param);
		Object result = aFileQuestionDto.getQuestion();
		assertEquals(param, result);
	}

	@Test
	void testResponse() {
		String param = "123";
		aFileQuestionDto.setResponse(param);
		Object result = aFileQuestionDto.getResponse();
		assertEquals(param, result);
	}

	@Test
	void testCategorie() {
		String param = "123";
		aFileQuestionDto.setCategorie(param);
		Object result = aFileQuestionDto.getCategorie();
		assertEquals(param, result);
	}

}

