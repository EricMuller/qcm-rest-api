package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookEntityTest {

	private WebHookEntity aWebHookEntity;

	public WebHookEntityTest() {
		this.aWebHookEntity = new WebHookEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aWebHookEntity.setId(param);
		Object result = aWebHookEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testUrl() {
		String param = "123";
		aWebHookEntity.setUrl(param);
		Object result = aWebHookEntity.getUrl();
		assertEquals(param, result);
	}

	@Test
	void testContentType() {
		String param = "123";
		aWebHookEntity.setContentType(param);
		Object result = aWebHookEntity.getContentType();
		assertEquals(param, result);
	}

	@Test
	void testSecret() {
		String param = "123";
		aWebHookEntity.setSecret(param);
		Object result = aWebHookEntity.getSecret();
		assertEquals(param, result);
	}

	@Test
	void testUser() {
		UserEntity param = new UserEntity();
		aWebHookEntity.setUser(param);
		Object result = aWebHookEntity.getUser();
		assertEquals(param, result);
	}

	@Test
	void testDefaultTimeOut() {
		Long param = Long.valueOf(123);
		aWebHookEntity.setDefaultTimeOut(param);
		Object result = aWebHookEntity.getDefaultTimeOut();
		assertEquals(param, result);
	}

}

