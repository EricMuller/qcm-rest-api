package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountGroupIdTest {

	private final AccountGroupId aAccountGroupId;

	public AccountGroupIdTest() {
		this.aAccountGroupId = new AccountGroupId();
	}
	@Test
	void testAccountId() {
		Long param = Long.valueOf(123);
		aAccountGroupId.setAccountId(param);
		Object result = aAccountGroupId.getAccountId();
		assertEquals(param, result);
	}

	@Test
	void testGroupId() {
		Long param = Long.valueOf(123);
		aAccountGroupId.setGroupId(param);
		Object result = aAccountGroupId.getGroupId();
		assertEquals(param, result);
	}

}

