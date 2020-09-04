package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuggestDtoTest {

	private SuggestDto aSuggestDto;

	public SuggestDtoTest() {
		this.aSuggestDto = new SuggestDto();
	}
	@Test
	void testFieldName() {
		String param = "123";
		aSuggestDto.setFieldName(param);
		Object result = aSuggestDto.getFieldName();
		assertEquals(param, result);
	}

	@Test
	void testLibelle() {
		String param = "123";
		aSuggestDto.setLibelle(param);
		Object result = aSuggestDto.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aSuggestDto.setId(param);
		Object result = aSuggestDto.getId();
		assertEquals(param, result);
	}

}

