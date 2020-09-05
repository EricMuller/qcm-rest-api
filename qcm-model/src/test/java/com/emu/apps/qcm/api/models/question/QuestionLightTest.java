package com.emu.apps.qcm.api.models.question;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionLightTest {

	private final QuestionLight aQuestionLight;

	public QuestionLightTest() {
		this.aQuestionLight = new QuestionLight();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aQuestionLight.setLibelle(param);
		Object result = aQuestionLight.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aQuestionLight.setType(param);
		Object result = aQuestionLight.getType();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aQuestionLight.setStatus(param);
		Object result = aQuestionLight.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aQuestionLight.setUuid(param);
		Object result = aQuestionLight.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aQuestionLight.setVersion(param);
		Object result = aQuestionLight.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionLight.setDateCreation(param);
		Object result = aQuestionLight.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aQuestionLight.setDateModification(param);
		Object result = aQuestionLight.getDateModification();
		assertEquals(param, result);
	}

}

