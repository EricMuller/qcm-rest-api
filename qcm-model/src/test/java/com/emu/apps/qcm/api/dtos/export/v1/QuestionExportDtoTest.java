package com.emu.apps.qcm.api.dtos.export.v1;

import com.emu.apps.qcm.api.dtos.export.v1.CategoryExportDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionExportDtoTest {

	private final QuestionExportDto aQuestionExportDto;

	public QuestionExportDtoTest() {
		this.aQuestionExportDto = new QuestionExportDto();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aQuestionExportDto.setLibelle(param);
		Object result = aQuestionExportDto.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aQuestionExportDto.setType(param);
		Object result = aQuestionExportDto.getType();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionExportDto.setStatus(param);
		Object result = aQuestionExportDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		CategoryExportDto param = new CategoryExportDto();
		aQuestionExportDto.setCategory(param);
		Object result = aQuestionExportDto.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aQuestionExportDto.setTip(param);
		Object result = aQuestionExportDto.getTip();
		assertEquals(param, result);
	}

	@Test
	void testPosition() {
		Long param = Long.valueOf(123);
		aQuestionExportDto.setPosition(param);
		Object result = aQuestionExportDto.getPosition();
		assertEquals(param, result);
	}

	@Test
	void testPoints() {
		Long param = Long.valueOf(123);
		aQuestionExportDto.setPoints(param);
		Object result = aQuestionExportDto.getPoints();
		assertEquals(param, result);
	}

}

