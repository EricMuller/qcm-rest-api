package com.emu.apps.qcm.rest.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
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

import java.util.Map;
import java.util.TreeMap;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;

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
    public Info managementInfo(){

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
    public Info technicalInfo(){

        return new Info()
                .title("Technical Rest API")
                .description("Technical Management")
                .version(buildProperties.getVersion() + "-" + gitProperties.getShortCommitId() + "-" + buildProperties.getTime())
                .contact(new Contact()
                        .name("Eric M.")
                        .url("https://qcm.webmarks.net")
                        .email("eric.pierre.muller@gmail.com"));
    }


    @Bean
    public Info qcmInfo(){

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
    public Info actuatorInfo(){

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

    @Bean SecurityScheme  KeyCoackSecurityScheme() {
        var authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return  new SecurityScheme()
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

    @Bean SecurityScheme  apiKeySecurityScheme() {
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
                    .addSecuritySchemes("keycloack_openid", KeyCoackSecurityScheme()   )

                    .addParameters("Version", new Parameter()
                            .in("header")
                            .name("Version")
                            .schema(new StringSchema())
                            .required(false)) ;
            openApi.info(managementInfo());
        };
    }

    @Bean
    public OpenApiCustomiser technicalApiCustomizer() {
        return openApi -> {
            openApi.addSecurityItem(new SecurityRequirement().addList("keycloack_openid"));
            openApi.getComponents()
                    .addSecuritySchemes("keycloack_openid", KeyCoackSecurityScheme()   );
            openApi.info(technicalInfo());
        };
    }

    @Bean
    GroupedOpenApi qcmApis() {
        return GroupedOpenApi.builder()
                .group("Qcm")
                .pathsToMatch(PUBLIC_API + "/**" )
                .addOpenApiCustomiser(qcmApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi actuatorApis() {
        return GroupedOpenApi.builder()
                .group("Actuator")
                .pathsToMatch(ACTUATOR_API + "/**")
                .addOpenApiCustomiser(actuatorApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi managementApis() {
        return GroupedOpenApi.builder()
                .group("Management")
                .pathsToMatch(MANAGEMENT_API + "/"
                        , MANAGEMENT_API + ACCOUNTS + "/**"
                        , MANAGEMENT_API + QUESTIONNAIRES + "/**"
                        , MANAGEMENT_API + QUESTIONS + "/**"
                        , MANAGEMENT_API + TAGS + "/**"
                        , MANAGEMENT_API + CATEGORIES + "/**"
                        , MANAGEMENT_API + EXPORTS + "/**"
                        , MANAGEMENT_API + IMPORTS + "/**"
                        , MANAGEMENT_API + UPLOADS + "/**"
                )
                .addOpenApiCustomiser(managementApiCustomizer())
                .build();
    }

    @Bean
    GroupedOpenApi technicalApis() {
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(technicalApiCustomizer())
                .group("Technical")
                .pathsToMatch(MANAGEMENT_API + WEBHOOKS + "/**",
                        MANAGEMENT_API + QUERY + "/**", LOGS + "/**")
                .build();
    }



}
