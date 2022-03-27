package com.emu.apps.qcm.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;

@Configuration
@Profile({"!test"})
public class OpenApi3Config {

    private BuildProperties buildProperties;

    private GitProperties gitProperties;

    private String authServer;

    private String realm;

    @Autowired
    public OpenApi3Config(BuildProperties buildProperties, GitProperties gitProperties,
                          @Value("${openapi.keycloak.auth-server-url}") String authServer,
                          @Value("${openapi.keycloak.realm}") String realm) {
        this.buildProperties = buildProperties;
        this.gitProperties = gitProperties;
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
                        .description("A Sample QCM Rest API")
                        .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                        .contact(new Contact()
                                .name("Eric M.")
                                .url("https://qcm.webmarks.net")
                                .email("eric.pierre.muller@gmail.com")));
    }

    @Bean
    GroupedOpenApi qcmHateosApis() {
        return GroupedOpenApi.builder()
                .group("Public")
                .pathsToMatch(PUBLICIZED_API + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi qcmApis() {
        return GroupedOpenApi.builder()
                .group("Application")
                .pathsToMatch(PROTECTED_API + "/"
                        , PROTECTED_API + ACCOUNTS + "/**"
                        , PROTECTED_API + QUESTIONNAIRES + "/**"
                        , PROTECTED_API + QUESTIONS + "/**"
                        , PROTECTED_API + TAGS + "/**"
                        , PROTECTED_API + CATEGORIES + "/**"
                        , PROTECTED_API + EXPORTS + "/**"
                        , PROTECTED_API + IMPORTS + "/**"
                        , PROTECTED_API + UPLOADS + "/**"
                )
                .build();
    }

    @Bean
    GroupedOpenApi settingsApis() {
        return GroupedOpenApi.builder()
                .group("Technical")
                .pathsToMatch(PROTECTED_API + WEBHOOKS + "/**"
                        , LOGS + "/**")
                .build();
    }

}
