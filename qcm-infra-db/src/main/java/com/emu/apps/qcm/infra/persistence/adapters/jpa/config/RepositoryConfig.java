package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.custom.SimpleJpaBulkRepositoryImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.jdbc")
@EnableJpaRepositories(
        basePackages = "com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories",
        repositoryBaseClass = SimpleJpaBulkRepositoryImpl.class
)
public class RepositoryConfig {
    private int batchSize;

    public int getBatchSize() {
        return batchSize;
    }

}

