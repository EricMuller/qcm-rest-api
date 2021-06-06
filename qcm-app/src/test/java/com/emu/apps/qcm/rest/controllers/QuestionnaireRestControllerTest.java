package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.resources.QuestionnaireResource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class QuestionnaireRestControllerTest {

    private static final String QUESTIONNAIRES_URI = ApiRestMappings.PUBLIC_API + ApiRestMappings.QUESTIONNAIRES;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static String TITLE = "tile";

    private static String TITLE2 = "tile2";

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }


    private QuestionnaireResource createQuestionnaire() {
        QuestionnaireResource questionnaire = new QuestionnaireResource();

        questionnaire.setTitle(TITLE);


        return questionnaire;
    }

    @Test
    @Transactional
    public void getQuestionnaireByIdShouldReturnQuestionnaire() {

        // create a new question
        final ResponseEntity <QuestionnaireResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONNAIRES_URI), HttpMethod.POST, new HttpEntity <>(createQuestionnaire(), headers()), QuestionnaireResource.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        URI uriGet = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONNAIRES_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        final ResponseEntity <QuestionnaireResource> getResponse = restTemplate
                .exchange(uriGet, HttpMethod.GET, new HttpEntity <>(headers()), QuestionnaireResource.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();


        assertThat(getResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE);
    }

    @Test
    @Transactional
    public void putQuestionnaireByIdShouldReturnSameQuestionnaire() {

        // create a new question
        final ResponseEntity <QuestionnaireResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONNAIRES_URI), HttpMethod.POST, new HttpEntity <>(createQuestionnaire(), headers()), QuestionnaireResource.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        assertThat(postResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE);

        String uuid = postResponse.getBody().getUuid();
        URI uriPut = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONNAIRES_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        postResponse.getBody().setTitle(TITLE2);

        final ResponseEntity <QuestionnaireResource> putResponse = restTemplate
                .exchange(uriPut, HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), headers()), QuestionnaireResource.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(putResponse.getBody()).isNotNull();
        assertThat(putResponse.getBody().getUuid()).isNotNull().isEqualTo(postResponse.getBody().getUuid());

        assertThat(putResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE2);
    }

}

