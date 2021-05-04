package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.resources.QuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionTagResources;
import com.emu.apps.qcm.rest.controllers.resources.ResponseResources;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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


    private QuestionResources createQuestion() {
        QuestionResources questionResources = new QuestionResources();
        questionResources.setQuestionText(QUESTION1);

        ResponseResources responseResources = new ResponseResources();
        responseResources.setResponseText(RESPONSE1);
        responseResources.setGood(true);

        questionResources.setResponses(Arrays.asList(responseResources));

        QuestionTagResources questionTagResources = new QuestionTagResources();
        questionTagResources.setLibelle(TAG1);

        questionResources.setQuestionTags(Sets.newHashSet(questionTagResources));

        return questionResources;
    }

    @Test
    public void postQuestionShouldCreateQuestionResponseAndTag() {

        // create a new question
        final ResponseEntity <QuestionResources> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST,
                        new HttpEntity <>(createQuestion(),
                                headers()), QuestionResources.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        assertThat(postResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        ResponseResources firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().isNotEmpty();
        QuestionTagResources questionTagDto = Iterables.getFirst(postResponse.getBody().getQuestionTags(), null);

        assertThat(questionTagDto).isNotNull();
        assertThat(questionTagDto.getLibelle()).isNotNull().isEqualTo(TAG1);
    }

    @Test
    public void postQuestionShouldCreateQuestionResponsesAndTags() {

        QuestionResources question = new QuestionResources();
        question.setQuestionText(QUESTION1);

        ResponseResources response1 = new ResponseResources();
        response1.setResponseText(RESPONSE1);
        response1.setGood(true);

        ResponseResources response2 = new ResponseResources();
        response2.setResponseText(RESPONSE2);
        response2.setGood(true);

        question.setResponses(Arrays.asList(response1, response2));

        QuestionTagResources questionTag1 = new QuestionTagResources();
        questionTag1.setLibelle(TAG1);
        QuestionTagResources questionTag2 = new QuestionTagResources();
        questionTag2.setLibelle(TAG2);

        question.setQuestionTags(Sets.newHashSet(questionTag1, questionTag2));


        // create a new question
        final ResponseEntity <QuestionResources> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(question, headers()), QuestionResources.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        assertThat(postResponse.getBody().getResponses()).isNotNull().hasSize(2);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().hasSize(2);

    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <QuestionResources> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestion(), headers()), QuestionResources.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <QuestionResources> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), QuestionResources.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();

        ResponseResources firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);
    }

    @Test
    @Transactional
    public void putQuestionShouldReturnUpdatedQuestion() {

        final HttpHeaders httpHeaders = headers();
        // create a new question
        final ResponseEntity <QuestionResources> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestion(), httpHeaders), QuestionResources.class);
        assertThat(postResponse.getBody()).isNotNull();

        ResponseResources firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        firstResponse.setResponseText(RESPONSE2);

        // put the question
        final ResponseEntity <QuestionResources> putResponse = restTemplate.exchange(
                getURL(QUESTIONS_URI), HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), httpHeaders), QuestionResources.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(putResponse.getBody().getUuid()).isNotNull();
        assertThat(putResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        firstResponse = Iterables.getFirst(putResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE2);
    }

}

