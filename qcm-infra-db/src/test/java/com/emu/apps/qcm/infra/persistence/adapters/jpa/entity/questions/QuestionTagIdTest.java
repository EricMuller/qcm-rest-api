package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagIdTest {

	private final QuestionTagId aQuestionTagId;

	public QuestionTagIdTest() {
		this.aQuestionTagId = new QuestionTagId();
	}
	@Test
	void testQuestionId() {
		Long param = Long.valueOf(123);
		aQuestionTagId.setQuestionId(param);
		Object result = aQuestionTagId.getQuestionId();
		assertEquals(param, result);
	}

	@Test
	void testTagId() {
		Long param = Long.valueOf(123);
		aQuestionTagId.setTagId(param);
		Object result = aQuestionTagId.getTagId();
		assertEquals(param, result);
	}

}

