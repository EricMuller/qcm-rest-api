package com.emu.apps.qcm.domains;

import com.emu.apps.qcm.domain.models.QuestionTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagTest {

	private final QuestionTag aQuestionTag;

	public QuestionTagTest() {
		this.aQuestionTag = new QuestionTag();
	}
	@Test
	void testUuid() {
		String param = "123";
		aQuestionTag.setUuid(param);
		Object result = aQuestionTag.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aQuestionTag.setLibelle(param);
		Object result = aQuestionTag.getLibelle();
		assertEquals(param, result);
	}

}

