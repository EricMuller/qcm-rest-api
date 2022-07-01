package com.emu.apps.qcm.rest.controllers.domain;


import com.emu.apps.qcm.domain.model.question.TypeQuestion;
import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionTagResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.ResponseResource;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {QuestionRestControllerIntegrationTest.Initializer.class})
class QuestionRestControllerIntegrationTest {

    @RegisterExtension
    static BaeldungPostgresqlExtension postgresqlContainer = BaeldungPostgresqlExtension.getInstance();

    static class Initializer
            implements ApplicationContextInitializer <ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    private static final String QUESTIONS_URI = DomainMappings.DOMAIN_API + DomainMappings.QUESTIONS;

    private static final String QUESTION1 = "Question 1";

    private static final String TAG1 = "tag 1";

    private static final String TAG2 = "tag 2";

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

    private ResourceFixture fixture = new ResourceFixture();

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }


    @BeforeAll
    private static void beforeAll(@Autowired DbRepositoryFixture dbFixture) {
        dbFixture.createAccountTest();
    }

    public class ResourceFixture {
        private ResourceFixture() {
        }

        public QuestionResource createQuestionResources() {

            var responseResource = new ResponseResource();
            responseResource.setResponseText(RESPONSE1);
            responseResource.setGood(true);

            var questionTagResources = new QuestionTagResource();
            questionTagResources.setLibelle(TAG1);

            var questionResource = new QuestionResource();
            questionResource.setQuestionText(QUESTION1);
            questionResource.setResponses(Arrays.asList(responseResource));

            questionResource.setTags(List.of(questionTagResources));
            questionResource.setType(TypeQuestion.FREE_TEXT.name());

            return questionResource;
        }
    }

    @Test
    void postQuestionShouldCreateQuestionResponseAndTag() {

        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST,
                        new HttpEntity <>(fixture.createQuestionResources(),
                                headers()), QuestionResource.class);
        assertAll(
                () -> assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED),
                () -> assertThat(postResponse.getBody().getUuid()).isNotNull(),
                () -> assertThat(postResponse.getBody().getResponses()).isNotNull().isNotEmpty());

        var firstResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);

        assertAll(
                () -> assertThat(firstResponse).isNotNull(),
                () -> assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1),
                () -> assertThat(postResponse.getBody().getTags()).isNotNull().isNotEmpty());

        var firstQuestionTag = Iterables.getFirst(postResponse.getBody().getTags(), null);

        assertAll(
                () -> assertThat(firstQuestionTag).isNotNull(),
                () -> assertThat(firstQuestionTag.getLibelle()).isNotNull().isEqualTo(TAG1));
    }

    @Test
    void postQuestionShouldCreateQuestionResponsesAndTags() {

        var question = new QuestionResource();
        question.setQuestionText(QUESTION1);

        var response1 = new ResponseResource();
        response1.setResponseText(RESPONSE1);
        response1.setGood(true);

        var response2 = new ResponseResource();
        response2.setResponseText(RESPONSE2);
        response2.setGood(true);

        question.setResponses(Arrays.asList(response1, response2));

        var tagResource1 = new QuestionTagResource();
        tagResource1.setLibelle(TAG1);

        var tagResource2 = new QuestionTagResource();
        tagResource2.setLibelle(TAG2);

        question.setTags(List.of(tagResource1, tagResource2));


        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(question, headers()), QuestionResource.class);

        assertAll(
                () -> assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED),
                () -> assertThat(postResponse.getBody().getUuid()).isNotNull(),
                () -> assertThat(postResponse.getBody().getResponses()).isNotNull().hasSize(2),
                () -> assertThat(postResponse.getBody().getTags()).isNotNull().hasSize(2));

    }


    @Test
    @Transactional
    void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        final var postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(fixture.createQuestionResources(), headers()), QuestionResource.class);

        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getUuid()).isNotNull();


        var uuid = postResponse.getBody().getUuid();
        var builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        var uri = builder.build().expand(uuid).encode().toUri();

        final var getResponse = restTemplate
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
    void putQuestionShouldReturnUpdatedQuestion() {

        var httpHeaders = headers();
        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(fixture.createQuestionResources(), httpHeaders), QuestionResource.class);


        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getUuid()).isNotNull();

        var firstPostResponse = Iterables.getFirst(postResponse.getBody().getResponses(), null);
        firstPostResponse.setResponseText(RESPONSE2);

        var uuid = postResponse.getBody().getUuid();
        var uriPut = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        // update the question
        var putResponse = restTemplate.exchange(
                uriPut, HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), httpHeaders), QuestionResource.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertAll(
                () -> assertThat(putResponse.getBody().getUuid()).isNotNull().isEqualTo(postResponse.getBody().getUuid()),
                () -> assertThat(putResponse.getBody().getResponses()).isNotNull().isNotEmpty());

        var firstPutResponse = Iterables.getFirst(putResponse.getBody().getResponses(), null);


        assertThat(firstPutResponse).isNotNull();
        assertThat(firstPutResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE2);
    }

}

