package com.emu.apps.qcm.spi.webmvc.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.*;

@Configuration
@Profile("webmvc")
public class OpenApiConfiguration {

    @Bean
    GroupedOpenApi qcmApis() {
        return GroupedOpenApi.builder().group("operations-qcm")
                .pathsToMatch(PUBLIC_API + QUESTIONNAIRES + "/**"
                        , PUBLIC_API + QUESTIONS + "/**"
                        , PUBLIC_API + TAGS + "/**"
                        , PUBLIC_API + CATEGORIES + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi exportApis() {
        return GroupedOpenApi.builder().group("operations-export")
                .pathsToMatch(PUBLIC_API + EXPORTS + "/**"
                        , PUBLIC_API + IMPORTS + "/**"
                        , PUBLIC_API + UPLOADS + "/**" )
                .build();
    }

    @Bean
    GroupedOpenApi settingsApis() {
        return GroupedOpenApi.builder().group("operations-setting")
                .pathsToMatch(PUBLIC_API + USERS + "/**"
                        , PUBLIC_API + WEBHOOKS + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi allApis() {
        return GroupedOpenApi.builder().group("all")
                .pathsToMatch("/**")
                .build();
    }
}
