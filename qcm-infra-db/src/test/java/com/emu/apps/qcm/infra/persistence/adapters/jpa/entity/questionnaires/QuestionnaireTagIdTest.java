package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagIdTest {

	private final QuestionnaireTagId aQuestionnaireTagId;

	public QuestionnaireTagIdTest() {
		this.aQuestionnaireTagId = new QuestionnaireTagId();
	}
	@Test
	void testQuestionnaireId() {
		Long param = Long.valueOf(123);
		aQuestionnaireTagId.setQuestionnaireId(param);
		Object result = aQuestionnaireTagId.getQuestionnaireId();
		assertEquals(param, result);
	}

	@Test
	void testTagId() {
		Long param = Long.valueOf(123);
		aQuestionnaireTagId.setTagId(param);
		Object result = aQuestionnaireTagId.getTagId();
		assertEquals(param, result);
	}

}

