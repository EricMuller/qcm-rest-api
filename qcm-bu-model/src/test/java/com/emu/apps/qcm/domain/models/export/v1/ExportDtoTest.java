package com.emu.apps.qcm.domain.models.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportDtoTest {

	private final Export aExportDto;

	public ExportDtoTest() {
		this.aExportDto = new Export();
	}
	@Test
	void testName() {
		String param = "123";
		aExportDto.setName(param);
		Object result = aExportDto.getName();
		assertEquals(param, result);
	}

	@Test
	void testVersionExport() {
		String param = "123";
		aExportDto.setVersionExport(param);
		Object result = aExportDto.getVersionExport();
		assertEquals(param, result);
	}

	@Test
	void testQuestionnaire() {
		QuestionnaireExport param = new QuestionnaireExport();
		aExportDto.setQuestionnaire(param);
		Object result = aExportDto.getQuestionnaire();
		assertEquals(param, result);
	}

}

