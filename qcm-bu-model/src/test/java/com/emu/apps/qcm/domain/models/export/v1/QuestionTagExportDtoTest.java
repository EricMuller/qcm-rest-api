package com.emu.apps.qcm.domain.models.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagExportDtoTest {

	private final QuestionTagExport aQuestionTagExportDto;

	public QuestionTagExportDtoTest() {
		this.aQuestionTagExportDto = new QuestionTagExport();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aQuestionTagExportDto.setLibelle(param);
		Object result = aQuestionTagExportDto.getLibelle();
		assertEquals(param, result);
	}

}

