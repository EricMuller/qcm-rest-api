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
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.CATEGORIES;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.QUESTIONNAIRES;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.QUESTIONS;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.*;
import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.SEARCH;
import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.SERVICES_API;
import static com.emu.apps.qcm.rest.controllers.unrestrained.PublicMappings.*;


@Configuration
@Profile({"!test"})
public class OpenApi3Config {

    private final BuildProperties buildProperties;

    private final GitProperties gitProperties;

    private final String authServer;

    private final String realm;

    private final String serverUri;

    @Autowired
    public OpenApi3Config(BuildProperties buildProperties, GitProperties gitProperties,
                          @Value("${openapi.keycloak.auth-server-url}") String authServer,
                          @Value("${openapi.keycloak.realm}") String realm,
                          @Value("${openapi.server.url}") String serverUri
    ) {
        this.buildProperties = buildProperties;
        this.gitProperties = gitProperties;
        this.authServer = authServer;
        this.realm = realm;
        this.serverUri = serverUri;
    }

    @Bean
    public Info managementInfo() {

        return new Info()
                .title("QCM Rest API")
                .description("A Sample QCM Rest API with Keycloak Authentication  <a href='https://keycloak.webmarks.net/'> here</a>")
                .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                .contact(new Contact()
                        .name("Eric M.")
                        .url("https://qcm.webmarks.net")
                        .email("eric.pierre.muller@gmail.com"));
    }

    @Bean
    public Info serviceInfo() {

        return new Info()
                .title("Technical Rest API")
                .description("Services Management with Keycloak Authentication  <a href='https://keycloak.webmarks.net/'> here</a>")
                .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                .contact(new Contact()
                        .name("Eric M.")
                        .url("https://qcm.webmarks.net")
                        .email("eric.pierre.muller@gmail.com"));
    }


    @Bean
    public Info qcmInfo() {

        return new Info()
                .title("QCM Rest API")
                .description("Public api without Authentication")
                .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                .contact(new Contact()
                        .name("Eric M.")
                        .url("https://qcm.webmarks.net")
                        .email("eric.pierre.muller@gmail.com"));
    }

    @Bean
    public Info actuatorInfo() {

        return new Info()
                .title("Spring Actuator API")
                .description("Public api without Authentication")
                .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                .contact(new Contact()
                        .name("Eric M.")
                        .url("https://qcm.webmarks.net")
                        .email("eric.pierre.muller@gmail.com"));
    }

    @Bean
    public OpenAPI openAPI() {
        var authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return new OpenAPI()
                .addServersItem(new Server().url(serverUri))
                .components(new Components()
                        .addParameters("Version", new Parameter()
                                .in("header")
                                .name("Version")
                                .schema(new StringSchema())
                                .required(false)))
                .info(managementInfo());
    }

    @Bean
    public OpenApiCustomiser qcmApiCustomizer() {
        return openApi -> openApi.info(qcmInfo());
    }

    @Bean
    public OpenApiCustomiser actuatorApiCustomizer() {
        return openApi -> openApi.info(actuatorInfo());
    }

    @Bean
    SecurityScheme KeyCoackSecurityScheme() {
        var authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("Oauth2 flow")
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl(authUrl + "/auth")
                                .refreshUrl(authUrl + "/token")
                                .tokenUrl(authUrl + "/token")
                                .scopes(new Scopes())
                        ));
    }

    @Bean
    SecurityScheme apiKeySecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .description("Api Key access")
                .in(SecurityScheme.In.HEADER)
                .name("X-API-KEY");
    }

    @Bean
    public OpenApiCustomiser managementApiCustomizer() {
        return openApi -> {
            openApi.addSecurityItem(new SecurityRequirement().addList("keycloack_openid"));
            openApi.getComponents()
                    // sso
                    .addSecuritySchemes("keycloack_openid", KeyCoackSecurityScheme())

                    .addParameters("Version", new Parameter()
                            .in("header")
                            .name("Version")
                            .schema(new StringSchema())
                            .required(false));
            openApi.info(managementInfo());
        };
    }

    @Bean
    public OpenApiCustomiser serviceApiCustomizer() {
        return openApi -> {
            openApi.addSecurityItem(new SecurityRequirement().addList("keycloack_openid"));
            openApi.getComponents()
                    .addSecuritySchemes("keycloack_openid", KeyCoackSecurityScheme());
            openApi.info(serviceInfo());
        };
    }

    @Bean
    GroupedOpenApi publicApis() {
        return GroupedOpenApi.builder()
                .group("Public api")
                .pathsToMatch(PUBLIC_API + "/**", LOGS_API + "/**")
                .addOpenApiCustomiser(qcmApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi actuatorApis() {
        return GroupedOpenApi.builder()
                .group("Actuator api")
                .pathsToMatch(ACTUATOR_API + "/**")
                .addOpenApiCustomiser(actuatorApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi managementApis() {
        return GroupedOpenApi.builder()
                .group("Domain Management api")
                .pathsToMatch(DOMAIN_API + "/"
                        , DOMAIN_API + ACCOUNTS + "/**"
                        , DOMAIN_API + QUESTIONNAIRES + "/**"
                        , DOMAIN_API + QUESTIONS + "/**"
                        , DOMAIN_API + CATEGORIES + "/**"
                        , DOMAIN_API + UPLOADS + "/**"
                        , DOMAIN_API + WEBHOOKS + "/**"
                )
                .addOpenApiCustomiser(managementApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi technicalApis() {
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(serviceApiCustomizer())
                .group("Services Management api")
                .pathsToMatch(SERVICES_API + SEARCH + "/**"
                        )
                .build();
    }

}
