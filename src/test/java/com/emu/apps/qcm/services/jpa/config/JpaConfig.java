package com.emu.apps.qcm.services.jpa.config;

import com.emu.apps.qcm.ApplicationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef="dateTimeProvider")
@Profile(value = {"test"})
public class JpaConfig {
    @Bean
    public AuditorAware auditorAware() {
        return new AuditorAware(){
            @Override
            public Object getCurrentAuditor() {
                return ApplicationTest.USER_TEST;
            }
        };
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }


}