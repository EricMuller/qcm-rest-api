package com.emu.apps.qcm.rest.config;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestHeaders {

    public static HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = new String(Base64.getEncoder().encode(
                (SpringBootJpaTestConfig.USER_TEST.getUuid() + ":" + SpringBootJpaTestConfig.USER_PASSWORD).getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + token);

        return headers;
    }

}
