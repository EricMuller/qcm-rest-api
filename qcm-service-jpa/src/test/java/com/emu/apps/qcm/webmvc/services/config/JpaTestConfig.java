package com.emu.apps.qcm.webmvc.services.config;

import com.emu.apps.qcm.H2TestProfileJPAConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@Profile(value = {"test"})
public class JpaTestConfig {
    @Bean
    public AuditorAware auditorAware() {
        return () -> Optional.of(H2TestProfileJPAConfig.USER_TEST);
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider();
    }


}
