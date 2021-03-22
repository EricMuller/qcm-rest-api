package com.emu.apps.qcm.domain.models.question;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagsTest {

	private final QuestionTags aQuestionTags;

	public QuestionTagsTest() {
		this.aQuestionTags = new QuestionTags();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aQuestionTags.setQuestionText(param);
		Object result = aQuestionTags.getQuestionText();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aQuestionTags.setType(param);
		Object result = aQuestionTags.getType();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionTags.setStatus(param);
		Object result = aQuestionTags.getStatus();
		assertEquals(param, result);
	}



	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionTags.setVersion(param);
		Object result = aQuestionTags.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionTags.setDateCreation(param);
		Object result = aQuestionTags.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionTags.setDateModification(param);
		Object result = aQuestionTags.getDateModification();
		assertEquals(param, result);
	}

}

