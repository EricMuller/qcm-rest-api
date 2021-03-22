package com.emu.apps.qcm.domains;

import java.time.ZonedDateTime;

import com.emu.apps.qcm.domain.models.base.DomainId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {

	private final DomainId aDomain;

	public DomainTest() {
		this.aDomain = new DomainId();
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aDomain.setVersion(param);
		Object result = aDomain.getVersion();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aDomain.setDateCreation(param);
		Object result = aDomain.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aDomain.setDateModification(param);
		Object result = aDomain.getDateModification();
		assertEquals(param, result);
	}

}

