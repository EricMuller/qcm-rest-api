package com.emu.apps.qcm.web.security;

import com.emu.apps.qcm.domain.config.SpringBootTestConfig;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

@NoArgsConstructor
public final class RestHeaders {

    public static HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = new String(Base64.getEncoder().encode(
                (SpringBootTestConfig.USER_TEST + ":" + SpringBootTestConfig.USER_PASSWORD).getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + token);

        return headers;
    }

}
