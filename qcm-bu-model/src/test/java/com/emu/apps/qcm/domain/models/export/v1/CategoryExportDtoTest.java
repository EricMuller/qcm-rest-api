package com.emu.apps.qcm.domain.models.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryExportDtoTest {

	private final CategoryExport aCategoryExportDto;

	public CategoryExportDtoTest() {
		this.aCategoryExportDto = new CategoryExport();
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

