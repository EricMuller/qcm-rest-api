package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.model.question.TypeQuestion;
import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.secured.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.ResponseResource;
import com.emu.apps.qcm.rest.controllers.secured.resources.TagResource;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class QuestionRestControllerTest {

    private static final String QUESTIONS_URI = ApiRestMappings.PROTECTED_API + ApiRestMappings.QUESTIONS;

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


    @BeforeAll
    private static void beforeAll(@Autowired DbRepositoryFixture dbFixture){
        dbFixture.createAccountTest();
    }

    private QuestionResource createQuestionResources() {
        QuestionResource questionResource = new QuestionResource();
        questionResource.setQuestionText(QUESTION1);

        ResponseResource responseResource = new ResponseResource();
        responseResource.setResponseText(RESPONSE1);
        responseResource.setGood(true);

        questionResource.setResponses(Arrays.asList(responseResource));

        var questionTagResources = new TagResource();
        questionTagResources.setLibelle(TAG1);

        questionResource.setTags(List.of(questionTagResources));

        questionResource.setType(TypeQuestion.FREE_TEXT.name());

        return questionResource;
    }

    @Test
    public void postQuestionShouldCreateQuestionResponseAndTag() {

        // create a new question
        final ResponseEntity <QuestionResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST,
                        new HttpEntity <>(createQuestionResources(),
                                headers()), QuestionResource.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        assertThat(postResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        ResponseResource firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

        assertThat(postResponse.getBody().getTags()).isNotNull().isNotEmpty();
        var questionTagDto = Iterables.getFirst(postResponse.getBody().getTags(), null);

        assertThat(questionTagDto).isNotNull();
        assertThat(questionTagDto.getLibelle()).isNotNull().isEqualTo(TAG1);
    }

    @Test
    public void postQuestionShouldCreateQuestionResponsesAndTags() {

        QuestionResource question = new QuestionResource();
        question.setQuestionText(QUESTION1);

        ResponseResource response1 = new ResponseResource();
        response1.setResponseText(RESPONSE1);
        response1.setGood(true);

        ResponseResource response2 = new ResponseResource();
        response2.setResponseText(RESPONSE2);
        response2.setGood(true);

        question.setResponses(Arrays.asList(response1, response2));

        var tagResource1 = new TagResource();
        tagResource1.setLibelle(TAG1);

        var tagResource2 = new TagResource();
        tagResource2.setLibelle(TAG2);

        question.setTags(List.of(tagResource1, tagResource2));


        // create a new question
        final ResponseEntity <QuestionResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(question, headers()), QuestionResource.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        assertThat(postResponse.getBody().getResponses()).isNotNull().hasSize(2);

        assertThat(postResponse.getBody().getTags()).isNotNull().hasSize(2);

    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <QuestionResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionResources(), headers()), QuestionResource.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <QuestionResource> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), QuestionResource.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getUuid()).isNotNull();

        ResponseResource firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);
    }

    @Test
    @Transactional
    public void putQuestionShouldReturnUpdatedQuestion() {

        final HttpHeaders httpHeaders = headers();
        // create a new question
        final ResponseEntity <QuestionResource> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionResources(), httpHeaders), QuestionResource.class);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        ResponseResource firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        firstResponse.setResponseText(RESPONSE2);

        String uuid = postResponse.getBody().getUuid();
        URI uriPut = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        // put the question
        final ResponseEntity <QuestionResource> putResponse = restTemplate.exchange(
                uriPut, HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), httpHeaders), QuestionResource.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(putResponse.getBody().getUuid()).isNotNull().isEqualTo(postResponse.getBody().getUuid());
        assertThat(putResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        firstResponse = Iterables.getFirst(putResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE2);
    }

}

