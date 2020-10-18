package com.emu.apps.qcm.api.dtos.export.v1;

import com.emu.apps.qcm.api.dtos.export.v1.CategoryExportDto;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireExportDtoTest {

	private final QuestionnaireExportDto aQuestionnaireExportDto;

	public QuestionnaireExportDtoTest() {
		this.aQuestionnaireExportDto = new QuestionnaireExportDto();
	}
	@Test
	void testTitle() {
		String param = "123";
		aQuestionnaireExportDto.setTitle(param);
		Object result = aQuestionnaireExportDto.getTitle();
		assertEquals(param, result);
	}

	@Test
	void testDescription() {
		String param = "123";
		aQuestionnaireExportDto.setDescription(param);
		Object result = aQuestionnaireExportDto.getDescription();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		CategoryExportDto param = new CategoryExportDto();
		aQuestionnaireExportDto.setCategory(param);
		Object result = aQuestionnaireExportDto.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionnaireExportDto.setStatus(param);
		Object result = aQuestionnaireExportDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testWebsite() {
		String param = "123";
		aQuestionnaireExportDto.setWebsite(param);
		Object result = aQuestionnaireExportDto.getWebsite();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aQuestionnaireExportDto.setUuid(param);
		Object result = aQuestionnaireExportDto.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionnaireExportDto.setVersion(param);
		Object result = aQuestionnaireExportDto.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaireExportDto.setDateCreation(param);
		Object result = aQuestionnaireExportDto.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaireExportDto.setDateModification(param);
		Object result = aQuestionnaireExportDto.getDateModification();
		assertEquals(param, result);
	}

}

