package com.emu.apps.qcm.webflux.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.ReactiveTransactionManager;


@Configuration
@RequiredArgsConstructor
@EnableR2dbcRepositories
public class R2DatabaseConfiguration extends AbstractR2dbcConfiguration {
    //
//    @Bean
//    public ReactiveCategoryRepository cityRepository(R2dbcRepositoryFactory r2dbcRepositoryFactory) {
//        return r2dbcRepositoryFactory.getRepository(ReactiveCategoryRepository.class);
//    }

//        @Bean
//        public R2dbcRepositoryFactory r2dbcRepositoryFactory() {
//            RelationalMappingContext context = new RelationalMappingContext();
//            context.afterPropertiesSet();
//            return new R2dbcRepositoryFactory(databaseClient(), context);
//        }

//    @Bean
//    public R2dbcRepositoryFactory repositoryFactory(DatabaseClient client) {
//        RelationalMappingContext context = new RelationalMappingContext();
//        context.afterPropertiesSet();
//
//        return new R2dbcRepositoryFactory(client, context);
//    }

    //
//    @Bean
//    public DatabaseClient databaseClient() {
//        return DatabaseClient.create(connectionFactory());
//    }
//


//    @Bean
//    public PostgresqlConnectionFactory connectionFactory(DatabaseProperties dsProperties) {
//        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration
//                .builder()
//                .host(dsProperties.getHost())
//                .database(dsProperties.getDatabase())
//                .username(dsProperties.getUsername())
//                .password(dsProperties.getPassword())
//                .build();
//        return new PostgresqlConnectionFactory(configuration);
//    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(5432)
                        .username("qcm")
                        .password("qcm")
                        .database("qcm")
                        .build());
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}

