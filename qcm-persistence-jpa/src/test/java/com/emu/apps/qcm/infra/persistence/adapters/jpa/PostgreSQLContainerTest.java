package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ContextConfiguration(initializers = {PostgreSQLContainerTest.Initializer.class})
public class PostgreSQLContainerTest {

    @Container
    static private final PostgreSQLContainer postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer()
            .withDatabaseName("postgres")
            .withUsername("test")
            .withPassword("test");

    @BeforeEach
    void beforeAll() {
        assertTrue(postgresqlContainer.isRunning());
    }

    static class Initializer
            implements ApplicationContextInitializer <ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
