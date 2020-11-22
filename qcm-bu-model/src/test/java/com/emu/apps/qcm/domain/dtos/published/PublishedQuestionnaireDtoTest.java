package com.emu.apps.qcm.domain.dtos.published;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublishedQuestionnaireDtoTest {

	private final PublishedQuestionnaireDto aPublishedQuestionnaireDto;

	public PublishedQuestionnaireDtoTest() {
		this.aPublishedQuestionnaireDto = new PublishedQuestionnaireDto();
	}
	@Test
	void testUuid() {
		String param = "123";
		aPublishedQuestionnaireDto.setUuid(param);
		Object result = aPublishedQuestionnaireDto.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testTitle() {
		String param = "123";
		aPublishedQuestionnaireDto.setTitle(param);
		Object result = aPublishedQuestionnaireDto.getTitle();
		assertEquals(param, result);
	}

	@Test
	void testDescription() {
		String param = "123";
		aPublishedQuestionnaireDto.setDescription(param);
		Object result = aPublishedQuestionnaireDto.getDescription();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		String param = "123";
		aPublishedQuestionnaireDto.setCategory(param);
		Object result = aPublishedQuestionnaireDto.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aPublishedQuestionnaireDto.setStatus(param);
		Object result = aPublishedQuestionnaireDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testWebsite() {
		String param = "123";
		aPublishedQuestionnaireDto.setWebsite(param);
		Object result = aPublishedQuestionnaireDto.getWebsite();
		assertEquals(param, result);
	}

	@Test
	void testPublished() {
		Boolean param = Boolean.TRUE;
		aPublishedQuestionnaireDto.setPublished(param);
		Object result = aPublishedQuestionnaireDto.getPublished();
		assertEquals(param, result);
	}

	@Test
	void testCreatedBy() {
		String param = "123";
		aPublishedQuestionnaireDto.setCreatedBy(param);
		Object result = aPublishedQuestionnaireDto.getCreatedBy();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aPublishedQuestionnaireDto.setVersion(param);
		Object result = aPublishedQuestionnaireDto.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aPublishedQuestionnaireDto.setDateCreation(param);
		Object result = aPublishedQuestionnaireDto.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aPublishedQuestionnaireDto.setDateModification(param);
		Object result = aPublishedQuestionnaireDto.getDateModification();
		assertEquals(param, result);
	}

}

