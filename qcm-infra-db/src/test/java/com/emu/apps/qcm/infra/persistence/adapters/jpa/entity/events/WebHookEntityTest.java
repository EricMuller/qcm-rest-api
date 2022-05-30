package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookEntityTest {

	private final WebHookEntity aWebHookEntity;

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
	void testOwner() {
		AccountEntity param = new AccountEntity();
		aWebHookEntity.setOwner(param);
		Object result = aWebHookEntity.getOwner();
		assertEquals(param, result);
	}

	@Test
	void testDefaultTimeOut() {
		Long param = Long.valueOf(123);
		aWebHookEntity.setDefaultTimeOut(param);
		Object result = aWebHookEntity.getDefaultTimeOut();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aWebHookEntity.setDateCreation(param);
		Object result = aWebHookEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aWebHookEntity.setDateModification(param);
		Object result = aWebHookEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aWebHookEntity.setUuid(param);
		Object result = aWebHookEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aWebHookEntity.setVersion(param);
		Object result = aWebHookEntity.getVersion();
		assertEquals(param, result);
	}

}

