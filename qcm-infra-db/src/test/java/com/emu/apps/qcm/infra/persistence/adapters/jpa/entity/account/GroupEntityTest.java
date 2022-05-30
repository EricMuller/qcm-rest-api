package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupEntityTest {

	private final GroupEntity aGroupEntity;

	public GroupEntityTest() {
		this.aGroupEntity = new GroupEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aGroupEntity.setId(param);
		Object result = aGroupEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testName() {
		String param = "123";
		aGroupEntity.setName(param);
		Object result = aGroupEntity.getName();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aGroupEntity.setUuid(param);
		Object result = aGroupEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aGroupEntity.setVersion(param);
		Object result = aGroupEntity.getVersion();
		assertEquals(param, result);
	}

}

