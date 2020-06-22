package com.emu.apps.qcm.infrastructure.adapters.jpa.config;

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
public class JpaConfig {

//    @Autowired
//    private AuditorAwareImpl auditorAware;

//    @Bean
//    public AuditorAware <String> auditorAware() {
//        return auditorAware;
//    }

    @Bean
    DateTimeProvider dateTimeProvider() {
            return new AuditingDateTimeProvider();
    }
}
