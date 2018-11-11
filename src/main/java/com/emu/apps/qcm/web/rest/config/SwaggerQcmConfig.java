package com.emu.apps.qcm.web.rest.config;

import com.emu.apps.qcm.web.rest.QcmApi;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
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
public class SwaggerQcmConfig {

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "Authorization", null);
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setRepositoryDetectionStrategy(
                        RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
            }
        };
    }

    @Bean
    public Docket productApiv1() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(QcmApi.API_V1)
//                .tags(new Tag("tags", "Repository for tags entities"))
                .select()
                .apis(withClassAnnotation(RestController.class))
                //RequestHandlerSelectors.basePackage("com.emu.apps.qcm"))
//                .apis(RequestHandlerSelectors.any())
                .paths(regex(QcmApi.API_V1 + ".*"))
//                .paths(PathSelectors.any())

                .build()
                .enable(true)
                .apiInfo(metaData(QcmApi.VERSION))
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

    List <SecurityReference> defaultAuth() {

        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("AUTHORIZATION", authorizationScopes));
    }

    private ApiInfo metaData(String version) {

        return new ApiInfo(
                "QCM Rest API",
                "",
                version,
                "Terms of service",
                new Contact("Eric MULLER", "https://qcm-designer.com", "eric.pierre.muller@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", Lists.newArrayList());
    }
}