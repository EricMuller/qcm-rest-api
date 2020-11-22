package com.emu.apps.qcm.domain.dtos.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportDtoTest {

	private final ExportDto aExportDto;

	public ExportDtoTest() {
		this.aExportDto = new ExportDto();
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
		QuestionnaireExportDto param = new QuestionnaireExportDto();
		aExportDto.setQuestionnaire(param);
		Object result = aExportDto.getQuestionnaire();
		assertEquals(param, result);
	}

}

