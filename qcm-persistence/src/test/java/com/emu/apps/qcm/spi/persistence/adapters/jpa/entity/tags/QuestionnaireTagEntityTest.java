package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagEntityTest {

	private final QuestionnaireTagEntity aQuestionnaireTagEntity;

	public QuestionnaireTagEntityTest() {
		this.aQuestionnaireTagEntity = new QuestionnaireTagEntity();
	}
	@Test
	void testId() {
		QuestionnaireTagId param = new QuestionnaireTagId();
		aQuestionnaireTagEntity.setId(param);
		Object result = aQuestionnaireTagEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testQuestionnaire() {
		QuestionnaireEntity param = new QuestionnaireEntity();
		aQuestionnaireTagEntity.setQuestionnaire(param);
		Object result = aQuestionnaireTagEntity.getQuestionnaire();
		assertEquals(param, result);
	}

	@Test
	void testTag() {
		Tag param = new Tag();
		aQuestionnaireTagEntity.setTag(param);
		Object result = aQuestionnaireTagEntity.getTag();
		assertEquals(param, result);
	}

}

