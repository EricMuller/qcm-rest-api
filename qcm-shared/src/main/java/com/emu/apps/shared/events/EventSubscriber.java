package com.emu.apps.shared.events;

public interface EventSubscriber {
    <E extends ApplicationEvent> void onEvent(E event);
}
