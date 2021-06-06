package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireTagId;
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
		TagEntity param = new TagEntity();
		aQuestionnaireTagEntity.setTag(param);
		Object result = aQuestionnaireTagEntity.getTag();
		assertEquals(param, result);
	}

}

