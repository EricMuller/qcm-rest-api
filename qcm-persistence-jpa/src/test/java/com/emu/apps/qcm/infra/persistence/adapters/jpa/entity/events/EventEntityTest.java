package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events;

import java.util.UUID;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventEntityTest {

	private final EventEntity aEventEntity;

	public EventEntityTest() {
		this.aEventEntity = new EventEntity();
	}
	@Test
	void testId() {
		Long param = Long.valueOf(123);
		aEventEntity.setId(param);
		Object result = aEventEntity.getId();
		assertEquals(param, result);
	}

	@Test
	void testOrigin() {
		UUID param = UUID.randomUUID();
		aEventEntity.setOrigin(param);
		Object result = aEventEntity.getOrigin();
		assertEquals(param, result);
	}

	@Test
	void testUuid() {
		UUID param = UUID.randomUUID();
		aEventEntity.setUuid(param);
		Object result = aEventEntity.getUuid();
		assertEquals(param, result);
	}

	@Test
	void testDateCreation() {
		ZonedDateTime param = ZonedDateTime.now();
		aEventEntity.setDateCreation(param);
		Object result = aEventEntity.getDateCreation();
		assertEquals(param, result);
	}

	@Test
	void testDateModification() {
		ZonedDateTime param = ZonedDateTime.now();
		aEventEntity.setDateModification(param);
		Object result = aEventEntity.getDateModification();
		assertEquals(param, result);
	}

	@Test
	void testVersion() {
		Long param = Long.valueOf(123);
		aEventEntity.setVersion(param);
		Object result = aEventEntity.getVersion();
		assertEquals(param, result);
	}

}

