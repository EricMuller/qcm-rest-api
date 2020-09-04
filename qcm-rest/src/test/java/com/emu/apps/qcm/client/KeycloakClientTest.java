package com.emu.apps.qcm.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Slf4j
class KeycloakClientTest {


    @Test
    void grantPasswordTest() throws IOException, InterruptedException {

        Multimap <String, String> parameters = ArrayListMultimap.create();
        parameters.put("grant_type", "password");
        parameters.put("client_id", "qcm-mobile-rest-api");
        parameters.put("username", "demo");
        parameters.put("password", "demodemo");

        HttpRequest requetePost = getHttpRequest(parameters);

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse response = httpClient.send(requetePost, HttpResponse.BodyHandlers.ofString());

        UncheckedObjectMapper uncheckedObjectMapper = new UncheckedObjectMapper();

        LOGGER.info("Status  : {}", response.statusCode());
        LOGGER.info("Headers : {}", response.headers());
        LOGGER.info("Body    : {}", response.body());

        Map <String, String> map = uncheckedObjectMapper.readValue((String) response.body());

        LOGGER.info("Body  map  : {} ", map);

        Assertions.assertTrue(!map.isEmpty());

    }


    @Test
    void authorisationCodePasswordTest() throws IOException, InterruptedException {

        Multimap <String, String> parameters = ArrayListMultimap.create();
        parameters.put("response_type", "code");
        parameters.put("client_id", "qcm-spi-rest");
//        parameters.put("scope", "email");
        parameters.put("state", "123456");

        HttpRequest requetePost = getHttpRequest(parameters);

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse response = httpClient.send(requetePost, HttpResponse.BodyHandlers.ofString());

        UncheckedObjectMapper uncheckedObjectMapper = new UncheckedObjectMapper();

        LOGGER.info("Status  : {}", response.statusCode());
        LOGGER.info("Headers : {}", response.headers());
        LOGGER.info("Body    : {}", response.body());

        Map <String, String> map = uncheckedObjectMapper.readValue((String) response.body());

        LOGGER.info("Body  map  : {}", map);

        Assertions.assertTrue(!map.isEmpty());
    }

    private HttpRequest getHttpRequest(Multimap <String, String> parameters) {
        String form = parameters.entries()
                .stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
                .collect(Collectors.joining("&"));


        return HttpRequest.newBuilder()
                .uri(URI.create("https://keycloak.webmarks.net/auth/realms/qcm/protocol/openid-connect/token"))
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
    }


    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        /**
         * Parses the given JSON string into a Map.
         */
        Map <String, String> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference <>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }

    }
}
