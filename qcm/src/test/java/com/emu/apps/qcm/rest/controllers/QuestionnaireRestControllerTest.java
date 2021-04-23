package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
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

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }


    private Questionnaire createQuestionnaireDto() {
        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setTitle(TITLE);


        return questionnaire;
    }

    @Test
    @Transactional
    public void getQuestionnaireByIdShouldReturnQuestionnaire() {

        // create a new question
        final ResponseEntity <Question> postResponse = restTemplate
                .exchange(getURL(QUESTIONNAIRES_URI), HttpMethod.POST, new HttpEntity <>(createQuestionnaireDto(), headers()), Question.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        String uuid = postResponse.getBody().getUuid();
        URI uriGet = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONNAIRES_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        final ResponseEntity <Questionnaire> getResponse = restTemplate
                .exchange(uriGet, HttpMethod.GET, new HttpEntity <>(headers()), Questionnaire.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(getResponse.getBody()).isNotNull();


        assertThat(getResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE);
    }


}

