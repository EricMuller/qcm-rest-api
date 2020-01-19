package com.emu.apps.qcm.webflux.handlers.clients;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientGatewayHandler {

    protected WebClient webClient;

    public WebClientGatewayHandler(WebClient webClient) {
        this.webClient = webClient;
    }

    protected String getServiceUri(ServerHttpRequest request) {
        return "http://localhost:8080" + request.getURI().getPath();
    }

    protected String getTokenValue(Authentication authentication) {
        String token = Strings.EMPTY;
        if (authentication instanceof JwtAuthenticationToken) {
            token = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
        }
        return token;
    }

}
