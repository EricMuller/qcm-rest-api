package com.emu.apps.qcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication // @Configuration  @ComponentScan @EnableAutoConfiguration
@EnableScheduling
// @EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = false)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ComponentScan("com.emu.apps")
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

