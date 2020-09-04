package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagIdTest {

	private QuestionnaireTagId aQuestionnaireTagId;

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

