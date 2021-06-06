package com.emu.apps.shared.application;

import com.emu.apps.shared.events.ApplicationEvent;
import com.emu.apps.shared.events.EventBus;
import com.emu.apps.shared.events.EventSubscriber;

import java.util.Map;

public interface ApplicationService {

    default <E extends ApplicationEvent> void publishEvent(E event) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.publish(event);
        }
    }

    default <E extends ApplicationEvent> void subscribe(String eventType, EventSubscriber subscriber) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.subscribe(eventType, subscriber);
        }
    }

    default <E extends ApplicationEvent> void unsubscribe(String eventType, EventSubscriber subscriber) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.unsubscribe(eventType, subscriber);
        }
    }

    EventBus getEventBus();

    default ApplicationEvent createEvent(String type, Map <String, String> payload) {

        return new ApplicationEvent(payload) {
            @Override
            public String getType() {
                return type;
            }
        };

    }
}
