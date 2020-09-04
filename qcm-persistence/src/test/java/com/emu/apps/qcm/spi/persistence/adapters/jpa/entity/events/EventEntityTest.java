package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventEntityTest {

	private EventEntity aEventEntity;

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

}

