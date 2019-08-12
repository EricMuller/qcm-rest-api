package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.Application;
import com.emu.apps.H2TestProfileJPAConfig;
import com.emu.apps.qcm.web.security.WebSecurityTestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = {Application.class, WebSecurityTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("http://localhost:${local.server.port}")
    private String host;

    protected final TestRestTemplate testRestTemplate = new TestRestTemplate();

    protected String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    protected HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = new String(Base64.getEncoder().encode(
                (H2TestProfileJPAConfig.USER_TEST + ":" + H2TestProfileJPAConfig.USER_PASSWORD).getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + token);

        return headers;
    }


    public RestTemplate getRestTemplate() {
        return testRestTemplate.getRestTemplate();
    }

}

