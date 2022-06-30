package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.AccountGroupEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.AccountGroupId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.GroupEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountGroupEntityTest {

	private final AccountGroupEntity aAccountGroupEntity;

	public AccountGroupEntityTest() {
		this.aAccountGroupEntity = new AccountGroupEntity();
	}
	@Test
	void testId() {
		AccountGroupId param = new AccountGroupId();
		aAccountGroupEntity.setId(param);
		Object result = aAccountGroupEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testAccount() {
		AccountEntity param = new AccountEntity();
		aAccountGroupEntity.setAccount(param);
		Object result = aAccountGroupEntity.getAccount();
		assertEquals(param, result);
	}

	@Test
	void testGroup() {
		GroupEntity param = new GroupEntity();
		aAccountGroupEntity.setGroup(param);
		Object result = aAccountGroupEntity.getGroup();
		assertEquals(param, result);
	}

}

