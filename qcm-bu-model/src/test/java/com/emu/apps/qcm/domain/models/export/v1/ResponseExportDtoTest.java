package com.emu.apps.qcm.domain.models.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseExportDtoTest {

	private final ResponseExport aResponseExportDto;

	public ResponseExportDtoTest() {
		this.aResponseExportDto = new ResponseExport();
	}
	@Test
	void testResponse() {
		String param = "123";
		aResponseExportDto.setResponseText(param);
		Object result = aResponseExportDto.getResponseText();
		assertEquals(param, result);
	}

	@Test
	void testGood() {
		Boolean param = Boolean.TRUE;
		aResponseExportDto.setGood(param);
		Object result = aResponseExportDto.getGood();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aResponseExportDto.setVersion(param);
		Object result = aResponseExportDto.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testNumber() {
		Long param = Long.valueOf(123);
		aResponseExportDto.setNumber(param);
		Object result = aResponseExportDto.getNumber();
		assertEquals(param, result);
	}

}

