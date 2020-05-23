package com.emu.apps.qcm.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef="dateTimeProvider")
@Profile("!test")
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
