package com.emu.apps.qcm.rest.controllers.domain;


import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireResource;
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

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {QuestionnaireRestControllerIntegrationTest.Initializer.class})
class QuestionnaireRestControllerIntegrationTest {

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

    private static final String QUESTIONNAIRES_URI = DomainMappings.DOMAIN_API + DomainMappings.QUESTIONNAIRES;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static String TITLE = "tile";

    private static String TITLE2 = "tile2";

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }


    private QuestionnaireResource createQuestionnaire() {
        var questionnaire = new QuestionnaireResource();

        questionnaire.setTitle(TITLE);
        return questionnaire;
    }

    @BeforeAll
    private static void beforeAll(@Autowired DbRepositoryFixture dbFixture) {
        dbFixture.createAccountTest();
    }

    @Test
    @Transactional
    void getQuestionnaireByIdShouldReturnQuestionnaire() {

        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONNAIRES_URI), HttpMethod.POST, new HttpEntity <>(createQuestionnaire(), headers()), QuestionnaireResource.class);
        assertThat(postResponse.getBody()).isNotNull();

        // get the question
        var uuid = postResponse.getBody().getUuid();
        var uriGet = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONNAIRES_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        var getResponse = restTemplate
                .exchange(uriGet, HttpMethod.GET, new HttpEntity <>(headers()), QuestionnaireResource.class);

        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();

        assertThat(getResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE);
    }

    @Test
    @Transactional
    void putQuestionnaireByIdShouldReturnSameQuestionnaire() {

        // create a new question
        var postResponse = restTemplate
                .exchange(getURL(QUESTIONNAIRES_URI), HttpMethod.POST, new HttpEntity <>(createQuestionnaire(), headers()), QuestionnaireResource.class);

        assertThat(postResponse.getBody()).isNotNull();

        assertAll(
                () -> assertThat(postResponse.getBody().getUuid()).isNotNull(),
                () -> assertThat(postResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE));

        var uuid = postResponse.getBody().getUuid();
        var uriPut = UriComponentsBuilder.fromHttpUrl(getURL(QUESTIONNAIRES_URI + "/{uuid}"))
                .build().expand(uuid).encode().toUri();

        postResponse.getBody().setTitle(TITLE2);

        var putResponse = restTemplate
                .exchange(uriPut, HttpMethod.PUT, new HttpEntity <>(postResponse.getBody(), headers()), QuestionnaireResource.class);

        assertThat(putResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(putResponse.getBody()).isNotNull();

        assertAll(
                () -> assertThat(putResponse.getBody().getUuid()).isNotNull().isEqualTo(postResponse.getBody().getUuid()),
                () -> assertThat(putResponse.getBody().getTitle()).isNotNull().isEqualTo(TITLE2));
    }

}

