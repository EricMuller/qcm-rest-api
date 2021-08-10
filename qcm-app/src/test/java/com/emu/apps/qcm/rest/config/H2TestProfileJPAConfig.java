package com.emu.apps.qcm.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static java.util.Collections.singletonMap;

@Configuration
@EnableJpaRepositories(basePackages = {"com.emu.apps"})
@EnableTransactionManagement
public class H2TestProfileJPAConfig {

    public static final String USERNAME_TEST = "user";
    public static final String USER_PASSWORD = "password";
 
    @Bean
    @Profile("test")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
 
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,  DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.emu.apps.qcm.services.jpa.entity","com.emu.apps.users.services.jpa.entity")
                .persistenceUnit("qcm")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "create-drop"))
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory
                                                                 entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
     
    // configure entityManagerFactory
    // configure transactionManager
    // configure additional Hibernate properties
}
