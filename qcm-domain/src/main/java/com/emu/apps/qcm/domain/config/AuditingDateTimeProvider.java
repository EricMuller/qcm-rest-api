package com.emu.apps.qcm.domain.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class AuditingDateTimeProvider implements DateTimeProvider {

    @Override
    public @NotNull Optional<TemporalAccessor> getNow() {
        return Optional.of(ZonedDateTime.now());
    }
}
