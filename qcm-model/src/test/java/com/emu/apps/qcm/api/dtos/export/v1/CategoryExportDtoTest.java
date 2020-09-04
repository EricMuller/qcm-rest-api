package com.emu.apps.qcm.api.dtos.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryExportDtoTest {

	private CategoryExportDto aCategoryExportDto;

	public CategoryExportDtoTest() {
		this.aCategoryExportDto = new CategoryExportDto();
	}
	@Test
	void testLibelle() {
		String param = "123";
		aCategoryExportDto.setLibelle(param);
		Object result = aCategoryExportDto.getLibelle();
		assertEquals(param, result);
	}

	@Test
	void testType() {
		String param = "123";
		aCategoryExportDto.setType(param);
		Object result = aCategoryExportDto.getType();
		assertEquals(param, result);
	}

}

