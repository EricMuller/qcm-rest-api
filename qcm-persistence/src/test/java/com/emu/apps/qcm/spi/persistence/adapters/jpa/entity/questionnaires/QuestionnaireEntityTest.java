package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireEntityTest {

	private QuestionnaireEntity aQuestionnaireEntity;

	public QuestionnaireEntityTest() {
		this.aQuestionnaireEntity = new QuestionnaireEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aQuestionnaireEntity.setId(param);
		Object result = aQuestionnaireEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testDescription() {
		String param = "123";
		aQuestionnaireEntity.setDescription(param);
		Object result = aQuestionnaireEntity.getDescription();
		assertEquals(param, result);
	}

	@Test
	void testTitle() {
		String param = "123";
		aQuestionnaireEntity.setTitle(param);
		Object result = aQuestionnaireEntity.getTitle();
		assertEquals(param, result);
	}

	@Test
	void testLocale() {
		String param = "123";
		aQuestionnaireEntity.setLocale(param);
		Object result = aQuestionnaireEntity.getLocale();
		assertEquals(param, result);
	}

	@Test
	void testWebsite() {
		String param = "123";
		aQuestionnaireEntity.setWebsite(param);
		Object result = aQuestionnaireEntity.getWebsite();
		assertEquals(param, result);
	}

	@Test
	void testPublished() {
		Boolean param = Boolean.valueOf(true);
		aQuestionnaireEntity.setPublished(param);
		Object result = aQuestionnaireEntity.getPublished();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		CategoryEntity param = new CategoryEntity();
		aQuestionnaireEntity.setCategory(param);
		Object result = aQuestionnaireEntity.getCategory();
		assertEquals(param, result);
	}

}

