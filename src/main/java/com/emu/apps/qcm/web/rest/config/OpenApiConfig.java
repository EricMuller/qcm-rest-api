package com.emu.apps.qcm.web.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${maven.version}") String version, @Value("${git.build.time}") String buildTime
    ) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("QCM API").version(version).description(
                        "QCM REST API ")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/mit-license.php"))
                        .contact(new Contact().name("Eric MULLER")
                                .url("https://qcm-rest-api.herokuapp.com/swagger-ui.html#")
                                .email("eric.pierre.muller@gmail.com"))
                        .description("Last build " + buildTime)
                );
    }
}
