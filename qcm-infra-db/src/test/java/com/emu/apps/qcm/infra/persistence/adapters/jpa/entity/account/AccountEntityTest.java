package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountEntityTest {

	private final AccountEntity aAccountEntity;

	public AccountEntityTest() {
		this.aAccountEntity = new AccountEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aAccountEntity.setId(param);
		Object result = aAccountEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testEmail() {
		String param = "123";
		aAccountEntity.setEmail(param);
		Object result = aAccountEntity.getEmail();
		assertEquals(param, result);
	}

	@Test
	void testUserName() {
		String param = "123";
		aAccountEntity.setUserName(param);
		Object result = aAccountEntity.getUserName();
		assertEquals(param, result);
	}

	@Test
	void testFirstName() {
		String param = "123";
		aAccountEntity.setFirstName(param);
		Object result = aAccountEntity.getFirstName();
		assertEquals(param, result);
	}

	@Test
	void testLastName() {
		String param = "123";
		aAccountEntity.setLastName(param);
		Object result = aAccountEntity.getLastName();
		assertEquals(param, result);
	}

	@Test
	void testCompany() {
		String param = "123";
		aAccountEntity.setCompany(param);
		Object result = aAccountEntity.getCompany();
		assertEquals(param, result);
	}

	@Test
	void testApiKey() {
		String param = "123";
		aAccountEntity.setApiKey(param);
		Object result = aAccountEntity.getApiKey();
		assertEquals(param, result);
	}

	@Test
	void testCreatedBy() {
		String param = "123";
		aAccountEntity.setCreatedBy(param);
		Object result = aAccountEntity.getCreatedBy();
		assertEquals(param, result);
	}

	@Test
	void testLastModifiedBy() {
		String param = "123";
		aAccountEntity.setLastModifiedBy(param);
		Object result = aAccountEntity.getLastModifiedBy();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aAccountEntity.setDateCreation(param);
		Object result = aAccountEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aAccountEntity.setDateModification(param);
		Object result = aAccountEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aAccountEntity.setUuid(param);
		Object result = aAccountEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aAccountEntity.setVersion(param);
		Object result = aAccountEntity.getVersion();
		assertEquals(param, result);
	}

}

