package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionIdTest {

	private final QuestionnaireQuestionId aQuestionnaireQuestionId;

	public QuestionnaireQuestionIdTest() {
		this.aQuestionnaireQuestionId = new QuestionnaireQuestionId();
	}
	@Test
	void testQuestionnaireId() {
		Long param = Long.valueOf(123);
		aQuestionnaireQuestionId.setQuestionnaireId(param);
		Object result = aQuestionnaireQuestionId.getQuestionnaireId();
		assertEquals(param, result);
	}

	@Test
	void testQuestionId() {
		Long param = Long.valueOf(123);
		aQuestionnaireQuestionId.setQuestionId(param);
		Object result = aQuestionnaireQuestionId.getQuestionId();
		assertEquals(param, result);
	}

}

