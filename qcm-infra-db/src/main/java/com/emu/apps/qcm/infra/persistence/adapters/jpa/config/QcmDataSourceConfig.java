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

//    @Primary
//    @Bean("dataSourceProperties")
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }

//    @Primary
//    @Bean(name = "dataSource")
//    @ConfigurationProperties("spring.datasource.hikari")
//    public HikariDataSource dataSourceQcm(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
//        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }

//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.emu.apps")
//                .persistenceUnit("qcm")
//                .properties(additionalProperties())
//                .build();
//    }

//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    private Map additionalProperties() {
//        Map <String, Object> properties = new HashMap <>();
//        properties.put("hibernate.ejb.naming_strategy", env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
//        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
//        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
//        properties.put("hibernate.connection.autocommit", "false");
//        //tells Hibernate that the underlying JDBC Connections already disabled the auto-commit mode.
//        properties.put("hibernate.connection.provider_disables_autocommit", "true");
//
//        properties.put("hibernate.id.new_generator_mappings", "true");
//        properties.put("hibernate.use-new-id-generator-mappings", "true");
//        return properties;
//    }

}
