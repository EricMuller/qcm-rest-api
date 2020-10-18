package com.emu.apps.qcm.spi.webmvc.rest.controllers;


import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.Response;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.api.models.QuestionTag;
import com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static com.emu.apps.qcm.spi.web.security.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class QuestionRestControllerTest {

    private static final String QUESTIONS_URI = ApiRestMappings.PUBLIC_API + ApiRestMappings.QUESTIONS;

    private static final String QUESTION1 = "Question 1";

    private static final String TAG1 = "tag 1";

    private static final String TAG2 = "tag 2";

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

        QuestionTag questionTagDto = new QuestionTag();
        questionTagDto.setLibelle(TAG1);

        questionDto.setQuestionTags(Sets.newHashSet(questionTagDto));

        return questionDto;
    }

    @Test
    public void postQuestionShouldCreateQuestionResponseAndTag() {

        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST,
                        new HttpEntity <>(createQuestionDto(),
                                headers()), Question.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        assertThat(postResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        Response firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().isNotEmpty();
        QuestionTag questionTagDto = Iterables.getFirst(postResponse.getBody().getQuestionTags(), null);

        assertThat(questionTagDto).isNotNull();
        assertThat(questionTagDto.getLibelle()).isNotNull().isEqualTo(TAG1);
    }

    @Test
    public void postQuestionShouldCreateQuestionResponsesAndTags() {

        Question questionDto = new Question();
        questionDto.setQuestionText(QUESTION1);

        Response responseDto1 = new Response();
        responseDto1.setResponseText(RESPONSE1);
        responseDto1.setGood(true);

        Response responseDto2 = new Response();
        responseDto2.setResponseText(RESPONSE2);
        responseDto2.setGood(true);

        questionDto.setResponses(Arrays.asList(responseDto1, responseDto2));

        QuestionTag questionTagDto1 = new QuestionTag();
        questionTagDto1.setLibelle(TAG1);
        QuestionTag questionTagDto2 = new QuestionTag();
        questionTagDto2.setLibelle(TAG2);

        questionDto.setQuestionTags(Sets.newHashSet(questionTagDto1, questionTagDto2));


        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(questionDto, headers()), Question.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        assertThat(postResponse.getBody().getResponses()).isNotNull().hasSize(2);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().hasSize(2);

    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionDto(), headers()), Question.class);
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

    @Test
    @Transactional
    public void putQuestionShouldReturnUpdatedQuestion() {

        final HttpHeaders httpHeaders = headers();
        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionDto(), httpHeaders), Question.class);
        assertThat(postResponse.getBody()).isNotNull();

        Response firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        firstResponse.setResponseText(RESPONSE2);

        // put the question
        final ResponseEntity <Question> putResponse = restTemplate.exchange(
                getURL(QUESTIONS_URI), HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), httpHeaders), Question.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(putResponse.getBody().getUuid()).isNotNull();
        assertThat(putResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        firstResponse = Iterables.getFirst(putResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE2);
    }

}

