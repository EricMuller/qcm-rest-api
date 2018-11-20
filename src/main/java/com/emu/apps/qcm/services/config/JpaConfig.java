package com.emu.apps.qcm.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef="dateTimeProvider")
public class JpaConfig {
    @Bean
    public AuditorAware <String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
            return new AuditingDateTimeProvider();
    }
}