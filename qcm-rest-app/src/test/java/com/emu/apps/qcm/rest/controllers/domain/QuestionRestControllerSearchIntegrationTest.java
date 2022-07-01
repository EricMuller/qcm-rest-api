package com.emu.apps.qcm.rest.controllers.domain;


import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionResource;
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

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {QuestionRestControllerSearchIntegrationTest.Initializer.class})
class QuestionRestControllerSearchIntegrationTest {

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

    private static final String RESPONSE1 = "response 1";

    private static final String RESPONSE2 = "response 2";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private QuestionResource createQuestion() {
        var questionResource = new QuestionResource();
        questionResource.setQuestionText(QUESTION1);
        questionResource.setNumeroVersion(2);

        var responseResource = new ResponseResource();
        responseResource.setResponseText(RESPONSE1);
        responseResource.setGood(true);


        questionResource.setResponses(Arrays.asList(responseResource));

        return questionResource;
    }

    @BeforeAll
    private static void beforeAll(@Autowired DbRepositoryFixture dbFixture) {
        dbFixture.createAccountTest();
    }


    @Test
    @Transactional
    void getQuestionByIdShouldReturnQuestion() {

        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONS_URI), HttpMethod.POST, new HttpEntity <>(createQuestion(), headers()), QuestionResource.class);


        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        var uuid = postResponse.getBody().getUuid();
        var builder = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONS_URI + "/{uuid}"));
        var uri = builder.build().expand(uuid).encode().toUri();

        var getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), QuestionResource.class);


        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertEquals(2, getResponse.getBody().getNumeroVersion());

        var firstResponse = Iterables.getFirst(getResponse.getBody().getResponses(), null);


        assertThat(firstResponse).isNotNull();
        assertThat(firstResponse.getResponseText()).isNotNull().isEqualTo(RESPONSE1);

    }

}
