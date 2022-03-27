package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.emu.apps"}
)
@Profile("!test")
public class QcmDataSourceConfig {

    private final Environment env;

    @Autowired
    public QcmDataSourceConfig(Environment env) {
        this.env = env;
    }

}
