package com.emu.apps.qcm.domain.models.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagExportDtoTest {

	private final QuestionnaireTagExport aQuestionnaireTagExportDto;

	public QuestionnaireTagExportDtoTest() {
		this.aQuestionnaireTagExportDto = new QuestionnaireTagExport();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aQuestionnaireTagExportDto.setLibelle(param);
		Object result = aQuestionnaireTagExportDto.getLibelle();
		assertEquals(param, result);
	}

}

