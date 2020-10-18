package com.emu.apps.qcm.api.models;

import com.emu.apps.qcm.api.models.Category;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionTest {

	private final QuestionnaireQuestion aQuestionnaireQuestion;

	public QuestionnaireQuestionTest() {
		this.aQuestionnaireQuestion = new QuestionnaireQuestion();
	}
	@Test
	void testQuestion() {
		String param = "123";
		aQuestionnaireQuestion.setQuestion(param);
		Object result = aQuestionnaireQuestion.getQuestion();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aQuestionnaireQuestion.setType(param);
		Object result = aQuestionnaireQuestion.getType();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		Category param = new Category();
		aQuestionnaireQuestion.setCategory(param);
		Object result = aQuestionnaireQuestion.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aQuestionnaireQuestion.setTip(param);
		Object result = aQuestionnaireQuestion.getTip();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionnaireQuestion.setStatus(param);
		Object result = aQuestionnaireQuestion.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testPosition() {
		Integer param = Integer.valueOf(123);
		aQuestionnaireQuestion.setPosition(param);
		Object result = aQuestionnaireQuestion.getPosition();
		assertEquals(param, result);
	}

	@Test
	void testPoints() {
		Integer param = Integer.valueOf(123);
		aQuestionnaireQuestion.setPoints(param);
		Object result = aQuestionnaireQuestion.getPoints();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aQuestionnaireQuestion.setUuid(param);
		Object result = aQuestionnaireQuestion.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionnaireQuestion.setVersion(param);
		Object result = aQuestionnaireQuestion.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaireQuestion.setDateCreation(param);
		Object result = aQuestionnaireQuestion.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionnaireQuestion.setDateModification(param);
		Object result = aQuestionnaireQuestion.getDateModification();
		assertEquals(param, result);
	}

}

