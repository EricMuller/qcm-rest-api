package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionEntityTest {

	private final QuestionnaireQuestionEntity aQuestionnaireQuestionEntity;

	public QuestionnaireQuestionEntityTest() {
		this.aQuestionnaireQuestionEntity = new QuestionnaireQuestionEntity();
	}
	@Test
	void testId() {
		QuestionnaireQuestionId param = new QuestionnaireQuestionId();
		aQuestionnaireQuestionEntity.setId(param);
		Object result = aQuestionnaireQuestionEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testQuestionnaire() {
		QuestionnaireEntity param = new QuestionnaireEntity();
		aQuestionnaireQuestionEntity.setQuestionnaire(param);
		Object result = aQuestionnaireQuestionEntity.getQuestionnaire();
		assertEquals(param, result);
	}

	@Test
	void testQuestion() {
		QuestionEntity param = new QuestionEntity();
		aQuestionnaireQuestionEntity.setQuestion(param);
		Object result = aQuestionnaireQuestionEntity.getQuestion();
		assertEquals(param, result);
	}

	@Test
	void testPosition() {
		Integer param = Integer.valueOf(123);
		aQuestionnaireQuestionEntity.setPosition(param);
		Object result = aQuestionnaireQuestionEntity.getPosition();
		assertEquals(param, result);
	}

	@Test
	void testPoints() {
		Integer param = Integer.valueOf(123);
		aQuestionnaireQuestionEntity.setPoints(param);
		Object result = aQuestionnaireQuestionEntity.getPoints();
		assertEquals(param, result);
	}

}

