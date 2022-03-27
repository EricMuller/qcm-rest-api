package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.secured.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.ResponseResource;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
 class QuestionRestControllerSearchTest {
    private static final String QUESTIONS_URI = ApiRestMappings.PROTECTED_API + ApiRestMappings.QUESTIONS;

    private static final String QUESTION1 = "Question 1";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private QuestionResource createQuestion() {
        QuestionResource questionResource = new QuestionResource();
        questionResource.setQuestionText(QUESTION1);

        ResponseResource responseResource = new ResponseResource();
        responseResource.setResponseText(RESPONSE1);
        responseResource.setGood(true);

        questionResource.setResponses(Arrays.asList(responseResource));

        return questionResource;
    }

    @BeforeAll
    private static void beforeAll(@Autowired DbRepositoryFixture dbFixture){
        dbFixture.createAccountTest();
    }


    @Test
    @Transactional

    void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <QuestionResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestion(), headers()), QuestionResource.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <QuestionResource> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), QuestionResource.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();

        ResponseResource firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

    }


}
