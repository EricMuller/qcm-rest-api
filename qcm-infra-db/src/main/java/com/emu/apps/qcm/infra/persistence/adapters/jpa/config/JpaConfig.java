package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef="dateTimeProvider")
@Profile("!test")
@EnableCaching
@EntityScan("com.emu.apps.qcm.infra.persistence.adapters.jpa.entity")
public class JpaConfig {

    @Bean
    DateTimeProvider dateTimeProvider() {
            return new AuditingDateTimeProvider();
    }
}
