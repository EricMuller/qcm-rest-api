package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.web.rest.QcmApi;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.ResponseDto;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class QuestionRestControllerSearchIntegrationTest extends SpringBootWebTestCase {

    private static final String QUESTIONS_URI = QcmApi.API_V1 + "/questions/";

    private static final String QUESTION1 = "Question 1";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

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


}
