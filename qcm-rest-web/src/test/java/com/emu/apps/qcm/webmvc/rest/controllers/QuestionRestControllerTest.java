package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.domain.dtos.QuestionDto;
import com.emu.apps.qcm.domain.dtos.QuestionTagDto;
import com.emu.apps.qcm.domain.dtos.ResponseDto;
import com.emu.apps.qcm.webmvc.rest.ApiRestMappings;
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

import static com.emu.apps.qcm.web.security.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class QuestionRestControllerTest {

    private static final String QUESTIONS_URI = ApiRestMappings.PUBLIC_API + "/questions/";

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


    private QuestionDto createQuestionDto() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion(QUESTION1);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponse(RESPONSE1);
        responseDto.setGood(true);

        questionDto.setResponses(Arrays.asList(responseDto));

        QuestionTagDto questionTagDto = new QuestionTagDto();
        questionTagDto.setLibelle(TAG1);

        questionDto.setQuestionTags(Sets.newHashSet(questionTagDto));

        return questionDto;
    }

    @Test
    public void postQuestionShouldCreateQuestionResponseAndTag() {

        // create a new question
        final ResponseEntity <QuestionDto> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST,
                        new HttpEntity <>(createQuestionDto(),
                                headers()), QuestionDto.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();
        assertThat(postResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        ResponseDto firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE1);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().isNotEmpty();
        QuestionTagDto questionTagDto = Iterables.getFirst(postResponse.getBody().getQuestionTags(), null);

        assertThat(questionTagDto).isNotNull();
        assertThat(questionTagDto.getLibelle()).isNotNull().isEqualTo(TAG1);
    }

    @Test
    public void postQuestionShouldCreateQuestionResponsesAndTags() {

        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion(QUESTION1);

        ResponseDto responseDto1 = new ResponseDto();
        responseDto1.setResponse(RESPONSE1);
        responseDto1.setGood(true);

        ResponseDto responseDto2 = new ResponseDto();
        responseDto2.setResponse(RESPONSE2);
        responseDto2.setGood(true);

        questionDto.setResponses(Arrays.asList(responseDto1, responseDto2));

        QuestionTagDto questionTagDto1 = new QuestionTagDto();
        questionTagDto1.setLibelle(TAG1);
        QuestionTagDto questionTagDto2 = new QuestionTagDto();
        questionTagDto2.setLibelle(TAG2);

        questionDto.setQuestionTags(Sets.newHashSet(questionTagDto1, questionTagDto2));


        // create a new question
        final ResponseEntity <QuestionDto> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(questionDto, headers()), QuestionDto.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        assertThat(postResponse.getBody().getResponses()).isNotNull().hasSize(2);

        assertThat(postResponse.getBody().getQuestionTags()).isNotNull().hasSize(2);

    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity <QuestionDto> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionDto(), headers()), QuestionDto.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <QuestionDto> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), QuestionDto.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();

        ResponseDto firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE1);
    }

    @Test
    @Transactional
    public void putQuestionShouldReturnUpdatedQuestion() {

        final HttpHeaders httpHeaders = headers();
        // create a new question
        final ResponseEntity <QuestionDto> postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestionDto(), httpHeaders), QuestionDto.class);
        assertThat(postResponse.getBody()).isNotNull();

        ResponseDto firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        firstResponse.setResponse(RESPONSE2);

        // put the question
        final ResponseEntity <QuestionDto> putResponse = restTemplate.exchange(
                getURL(QUESTIONS_URI), HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), httpHeaders), QuestionDto.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(putResponse.getBody().getUuid()).isNotNull();
        assertThat(putResponse.getBody().getResponses()).isNotNull().isNotEmpty();

        firstResponse = Iterables.getFirst(putResponse.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE2);
    }
}

