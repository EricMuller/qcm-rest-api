package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagEntityTest {

	private QuestionTagEntity aQuestionTagEntity;

	public QuestionTagEntityTest() {
		this.aQuestionTagEntity = new QuestionTagEntity();
	}
	@Test
	void testId() {
		QuestionTagId param = new QuestionTagId();
		aQuestionTagEntity.setId(param);
		Object result = aQuestionTagEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testQuestion() {
		QuestionEntity param = new QuestionEntity();
		aQuestionTagEntity.setQuestion(param);
		Object result = aQuestionTagEntity.getQuestion();
		assertEquals(param, result);
	}

	@Test
	void testTag() {
		Tag param = new Tag();
		aQuestionTagEntity.setTag(param);
		Object result = aQuestionTagEntity.getTag();
		assertEquals(param, result);
	}

}

