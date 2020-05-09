package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.services.config.SpringBootTestConfig;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.ResponseDto;
import com.emu.apps.qcm.webmvc.rest.RestMapping;
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

import static com.emu.apps.qcm.web.security.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class QuestionRestControllerSearchTest {

    private static final String QUESTIONS_URI = RestMapping.API + "/questions/";

    private static final String QUESTION1 = "Question 1";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }


    private QuestionDto createQuestionDto() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion(QUESTION1);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponse(RESPONSE1);
        responseDto.setGood(true);

        questionDto.setResponses(Arrays.asList(responseDto));

        return questionDto;
    }

    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity<QuestionDto> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity<>(createQuestionDto(), headers()), QuestionDto.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        Long id = postResponse.getBody().getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "{id}"));
        URI uri = builder.build().expand(id).encode().toUri();

        final ResponseEntity<QuestionDto> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity<>(headers()), QuestionDto.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();

        ResponseDto firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE1);
    }


}
