package com.emu.apps.qcm.spi.webmvc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.*;

@Configuration
@Profile("webmvc")
public class OpenApi3Config {

    private BuildProperties buildProperties;

    private String authServer;

    private String realm;

    @Autowired
    public OpenApi3Config(BuildProperties buildProperties,
                          @Value("${keycloak.auth-server-url}") String authServer,
                          @Value("${keycloak.realm}") String realm) {
        this.buildProperties = buildProperties;
        this.authServer = authServer;
        this.realm = realm;
    }

    @Bean
    public OpenAPI openAPI() {
        var authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("keycloack_openid", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Oauth2 flow")
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(authUrl + "/auth")
                                                .refreshUrl(authUrl + "/token")
                                                .tokenUrl(authUrl + "/token")
                                                .scopes(new Scopes())
                                        )))
                        .addSecuritySchemes("api_key", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .description("Api Key access")
                                .in(SecurityScheme.In.HEADER)
                                .name("API-KEY")
                        )
                        .addParameters("Version", new Parameter()
                                .in("header")
                                .name("Version")
                                .schema(new StringSchema())
                                .required(false)))
                .security(Arrays.asList(
                        new SecurityRequirement().addList("keycloack_openid"),
                        new SecurityRequirement().addList("api_key")))

                .info(new Info()
                        .title("QCM Rest API")
                        .description("QCM Rest API")
                        .version(this.buildProperties.getVersion())
                        .contact(new Contact()
                                .name("Eric M.")
                                .url("https://qcm-rest-api.herokuapp.com/")
                                .email("eric.pierre.muller@gmail.com")));
    }

    @Bean
    GroupedOpenApi qcmApis() {
        return GroupedOpenApi.builder().group("basics")
                .pathsToMatch(PUBLIC_API + USERS + "/**"
                        , PUBLIC_API + QUESTIONNAIRES + "/**"
                        , PUBLIC_API + QUESTIONS + "/**"
                        , PUBLIC_API + TAGS + "/**"
                        , PUBLIC_API + CATEGORIES + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi exportApis() {
        return GroupedOpenApi.builder().group("exports")
                .pathsToMatch(PUBLIC_API + EXPORTS + "/**"
                        , PUBLIC_API + IMPORTS + "/**"
                        , PUBLIC_API + UPLOADS + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi settingsApis() {
        return GroupedOpenApi.builder().group("notifications")
                .pathsToMatch(PUBLIC_API + WEBHOOKS + "/**")
                .build();
    }

}
