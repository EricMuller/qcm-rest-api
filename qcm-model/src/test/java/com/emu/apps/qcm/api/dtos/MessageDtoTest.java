package com.emu.apps.qcm.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageDtoTest {

	private final MessageDto aMessageDto;

	public MessageDtoTest() {
		this.aMessageDto = new MessageDto();
	}
	@Test
	void testStatus() {
		String param = "123";
		aMessageDto.setStatus(param);
		Object result = aMessageDto.getStatus();
		assertEquals(param, result);
	}

	@Test
	void testMessage() {
		String param = "123";
		aMessageDto.setMessage(param);
		Object result = aMessageDto.getMessage();
		assertEquals(param, result);
	}

	@Test
	void testData() {
		String param = "123";
		aMessageDto.setData(param);
		Object result = aMessageDto.getData();
		assertEquals(param, result);
	}

}

