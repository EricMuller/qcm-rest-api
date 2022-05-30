package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccredProfilEntityTest {

	private final AccredProfilEntity aAccredProfilEntity;

	public AccredProfilEntityTest() {
		this.aAccredProfilEntity = new AccredProfilEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aAccredProfilEntity.setId(param);
		Object result = aAccredProfilEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testName() {
		String param = "123";
		aAccredProfilEntity.setName(param);
		Object result = aAccredProfilEntity.getName();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aAccredProfilEntity.setUuid(param);
		Object result = aAccredProfilEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aAccredProfilEntity.setVersion(param);
		Object result = aAccredProfilEntity.getVersion();
		assertEquals(param, result);
	}

}

