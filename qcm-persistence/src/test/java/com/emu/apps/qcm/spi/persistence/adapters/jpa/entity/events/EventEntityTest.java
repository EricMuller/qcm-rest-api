package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventEntityTest {
private EventEntity pojoObject;
public EventEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events.EventEntity();
}
@Test
void testId() {
Long param = Long.valueOf(123);
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testOrigin() {
UUID param = UUID.randomUUID();
pojoObject.setOrigin(param);
Object result = pojoObject.getOrigin();
assertEquals(param, result);
}

}

