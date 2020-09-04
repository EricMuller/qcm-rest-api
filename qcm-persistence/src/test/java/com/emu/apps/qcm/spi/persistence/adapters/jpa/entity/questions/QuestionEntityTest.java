package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionEntityTest {

	private QuestionEntity aQuestionEntity;

	public QuestionEntityTest() {
		this.aQuestionEntity = new QuestionEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aQuestionEntity.setId(param);
		Object result = aQuestionEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testMandatory() {
		Boolean param = Boolean.valueOf(true);
		aQuestionEntity.setMandatory(param);
		Object result = aQuestionEntity.getMandatory();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aQuestionEntity.setLibelle(param);
		Object result = aQuestionEntity.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testCategory() {
		CategoryEntity param = new CategoryEntity();
		aQuestionEntity.setCategory(param);
		Object result = aQuestionEntity.getCategory();
		assertEquals(param, result);
	}

	@Test
	void testTip() {
		String param = "123";
		aQuestionEntity.setTip(param);
		Object result = aQuestionEntity.getTip();
		assertEquals(param, result);
	}

}

