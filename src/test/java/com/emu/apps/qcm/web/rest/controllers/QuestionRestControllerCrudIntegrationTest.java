package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.web.rest.QcmApi;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionTagDto;
import com.emu.apps.qcm.web.rest.dtos.ResponseDto;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
public class QuestionRestControllerCrudIntegrationTest extends RestControllerIntegrationTest {

    private static final String QUESTIONS_URI = QcmApi.API_V1 + "/questions/";

    private static final String QUESTION1 = "Question 1";

    private static final String TAG1 = "tag 1";

    private static final String TAG2 = "tag 2";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

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
        final ResponseEntity<QuestionDto> responsePost = getRestTemplate()
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity<>(createQuestionDto(), getHeaders()), QuestionDto.class);

        assertThat(responsePost.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responsePost.getBody().getId()).isNotNull();
        assertThat(responsePost.getBody().getResponses()).isNotNull().isNotEmpty();

        ResponseDto firstResponse = Iterables.getFirst(responsePost.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE1);

        assertThat(responsePost.getBody().getQuestionTags()).isNotNull().isNotEmpty();
        QuestionTagDto questionTagDto = Iterables.getFirst(responsePost.getBody().getQuestionTags(), null);

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
        final ResponseEntity<QuestionDto> responsePost = getRestTemplate()
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity<>(questionDto, getHeaders()), QuestionDto.class);

        assertThat(responsePost.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responsePost.getBody().getId()).isNotNull();

        assertThat(responsePost.getBody().getResponses()).isNotNull().hasSize(2);

        assertThat(responsePost.getBody().getQuestionTags()).isNotNull().hasSize(2);

    }


    @Test
    @Transactional
    public void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final ResponseEntity<QuestionDto> responsePost = getRestTemplate()
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity<>(createQuestionDto(), getHeaders()), QuestionDto.class);
        assertThat(responsePost.getBody()).isNotNull();

        // get the question
        Long id = responsePost.getBody().getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "{id}"));
        URI uri = builder.build().expand(id).encode().toUri();

        final ResponseEntity<QuestionDto> responseGet = getRestTemplate()
                .exchange(uri, HttpMethod.GET, new HttpEntity<>(getHeaders()), QuestionDto.class);

        assertThat(responseGet.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(responseGet.getBody()).isNotNull();

        ResponseDto firstResponse = Iterables.getFirst(responseGet.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE1);
    }

    @Test
    @Transactional
    public void putQuestionShouldReturnUpdatedQuestion() {

        final HttpHeaders httpHeaders = getHeaders();
        // create a new question
        final ResponseEntity<QuestionDto> responsePost = getRestTemplate()
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity<>(createQuestionDto(), httpHeaders), QuestionDto.class);
        assertThat(responsePost.getBody()).isNotNull();


        ResponseDto firstResponse = Iterables.getFirst(responsePost.getBody().getResponses(), null);
        firstResponse.setResponse(RESPONSE2);

        // put the question
        final ResponseEntity<QuestionDto> responsePut = getRestTemplate().exchange(
                getURL(QUESTIONS_URI), HttpMethod.PUT, new HttpEntity<>(responsePost.getBody(), httpHeaders), QuestionDto.class);

        assertThat(responsePut.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responsePut.getBody().getId()).isNotNull();
        assertThat(responsePut.getBody().getResponses()).isNotNull().isNotEmpty();

        firstResponse = Iterables.getFirst(responsePut.getBody().getResponses(), null);
        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponse()).isNotNull().isEqualTo(RESPONSE2);
    }
}

