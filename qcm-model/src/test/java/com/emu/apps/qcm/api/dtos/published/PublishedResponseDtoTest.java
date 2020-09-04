package com.emu.apps.qcm.api.dtos.published;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublishedResponseDtoTest {

	private final PublishedResponseDto aPublishedResponseDto;

	public PublishedResponseDtoTest() {
		this.aPublishedResponseDto = new PublishedResponseDto();
	}
	@Test
	void testResponse() {
		String param = "123";
		aPublishedResponseDto.setResponse(param);
		Object result = aPublishedResponseDto.getResponse();
		assertEquals(param, result);
	}

	@Test
	void testStatus() {
		String param = "123";
		aPublishedResponseDto.setStatus(param);
		Object result = aPublishedResponseDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testGood() {
		Boolean param = Boolean.TRUE;
		aPublishedResponseDto.setGood(param);
		Object result = aPublishedResponseDto.getGood();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aPublishedResponseDto.setVersion(param);
		Object result = aPublishedResponseDto.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testNumber() {
		Long param = Long.valueOf(123);
		aPublishedResponseDto.setNumber(param);
		Object result = aPublishedResponseDto.getNumber();
		assertEquals(param, result);
	}

}

