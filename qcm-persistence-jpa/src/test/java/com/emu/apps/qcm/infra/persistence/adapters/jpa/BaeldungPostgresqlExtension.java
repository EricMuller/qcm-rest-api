package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class BaeldungPostgresqlExtension extends PostgreSQLContainer <BaeldungPostgresqlExtension>
        implements BeforeAllCallback, AfterAllCallback {
    private static final String IMAGE_VERSION = "postgres:11.1";

    private static BaeldungPostgresqlExtension container;

    private BaeldungPostgresqlExtension() {
        super(IMAGE_VERSION);
    }

    public static BaeldungPostgresqlExtension getInstance() {
        if (container == null) {
            container = new BaeldungPostgresqlExtension()
                    .withDatabaseName("integration-tests-db")
                    .withUsername("sa")
                    .withPassword("sa");
            container.start();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
//        System.setProperty("DB_URL", container.getJdbcUrl());
//        System.setProperty("DB_USERNAME", container.getUsername());
//        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    //
    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

    }
}
