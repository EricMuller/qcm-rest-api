package com.emu.apps.qcm.domains;

import java.time.ZonedDateTime;

import com.emu.apps.qcm.domain.models.WebHook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookTest {

	private final WebHook aWebHook;

	public WebHookTest() {
		this.aWebHook = new WebHook();
	}
	@Test
	void testUrl() {
		String param = "123";
		aWebHook.setUrl(param);
		Object result = aWebHook.getUrl();
		assertEquals(param, result);
	}

	@Test
	void testSecret() {
		String param = "123";
		aWebHook.setSecret(param);
		Object result = aWebHook.getSecret();
		assertEquals(param, result);
	}

	@Test
	void testDefaultTimeOut() {
		Long param = Long.valueOf(123);
		aWebHook.setDefaultTimeOut(param);
		Object result = aWebHook.getDefaultTimeOut();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		String param = "123";
		aWebHook.setUuid(param);
		Object result = aWebHook.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aWebHook.setVersion(param);
		Object result = aWebHook.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aWebHook.setDateCreation(param);
		Object result = aWebHook.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aWebHook.setDateModification(param);
		Object result = aWebHook.getDateModification();
		assertEquals(param, result);
	}

}

