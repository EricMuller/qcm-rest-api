package com.emu.apps.users.web.rest.config;


import com.emu.apps.users.web.rest.UserApi;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2WebMvc
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerUsersConfig {

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "Authorization", null);
    }

    @Bean
    public Docket usersApiv1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(UserApi.API_V1)
                .select()
                .apis(withClassAnnotation(RestController.class))
                .paths(regex(UserApi.API_V1 + ".*"))
                .build()
                .enable(true)
                .apiInfo(metaData(UserApi.VERSION))
                .produces(Collections.singleton("application/json"))
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }


    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("/anyPath.*"))
                .build();
    }

    private List <SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("AUTHORIZATION", authorizationScopes));
    }

    private ApiInfo metaData(String version) {

        return new ApiInfo(
                "Users Rest API",
                "",
                version,
                "Terms of service",
                new Contact("Eric MULLER", "https://webmarks.net", "eric.pierre.muller@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", Lists.newArrayList());
    }
}