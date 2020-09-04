package com.emu.apps.qcm.api.models;

import com.emu.apps.qcm.api.models.Category;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTest {

	private Questionnaire aQuestionnaire;

	public QuestionnaireTest() {
		this.aQuestionnaire = new Questionnaire();
	}
	@Test
	void testTitle() {
		String param = "123";
		aQuestionnaire.setTitle(param);
		Object result = aQuestionnaire.getTitle();
		assertEquals(param, result);
	}

	@Test
	void testDescription() {
		String param = "123";
		aQuestionnaire.setDescription(param);
		Object result = aQuestionnaire.getDescription();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		Category param = new Category();
		aQuestionnaire.setCategory(param);
		Object result = aQuestionnaire.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionnaire.setStatus(param);
		Object result = aQuestionnaire.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testWebsite() {
		String param = "123";
		aQuestionnaire.setWebsite(param);
		Object result = aQuestionnaire.getWebsite();
		assertEquals(param, result);
	}

	@Test
	void testPublished() {
		Boolean param = Boolean.valueOf(true);
		aQuestionnaire.setPublished(param);
		Object result = aQuestionnaire.getPublished();
		assertEquals(param, result);
	}

	@Test
	void testCreatedBy() {
		String param = "123";
		aQuestionnaire.setCreatedBy(param);
		Object result = aQuestionnaire.getCreatedBy();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aQuestionnaire.setUuid(param);
		Object result = aQuestionnaire.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionnaire.setVersion(param);
		Object result = aQuestionnaire.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaire.setDateCreation(param);
		Object result = aQuestionnaire.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaire.setDateModification(param);
		Object result = aQuestionnaire.getDateModification();
		assertEquals(param, result);
	}

}

