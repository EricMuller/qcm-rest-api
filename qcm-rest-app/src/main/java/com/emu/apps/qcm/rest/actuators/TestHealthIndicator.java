package com.emu.apps.qcm.rest.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("testHealthIndicator")
//@ConditionalOnEnabledHealthIndicator("isAlive")
public class TestHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        return Health.up()
                .withDetail("test", "Super green")
                .withDetail("test database", "OK call in 10ms").build();
    }

    private boolean isRunningDB(){

        return true;
    }
}
