package com.emu.apps.shared.events;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public abstract class ApplicationEvent {
    protected Map <String, String> payload;

    public abstract String getType();

    public String getPayloadValue(String key) {
        if (this.payload.containsKey(key)) {
            return this.payload.get(key);
        }
        return EMPTY;
    }

    public ApplicationEvent(Map <String, String> payload) {
        this.payload = payload;
    }
}

