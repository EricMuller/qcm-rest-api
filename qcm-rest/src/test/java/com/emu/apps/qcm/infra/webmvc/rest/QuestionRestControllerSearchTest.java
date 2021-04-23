package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.google.common.collect.Iterables;
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
import java.util.Arrays;

import static com.emu.apps.qcm.infra.web.security.RestHeaders.headers;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.QUESTIONS;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")

public class QuestionRestControllerSearchTest {
    private static final String QUESTIONS_URI = PUBLIC_API + QUESTIONS;

    private static final String QUESTION1 = "Question 1";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private Question createQuestionDto() {
        Question questionDto = new Question();
        questionDto.setQuestionText(QUESTION1);

        Response responseDto = new Response();
        responseDto.setResponseText(RESPONSE1);
        responseDto.setGood(true);

        questionDto.setResponses(Arrays.asList(responseDto));

        return questionDto;
    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionDto(), headers()), Question.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <Question> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), Question.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();

        Response firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

    }


}
