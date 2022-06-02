package com.emu.apps.qcm.rest.controllers.management;


import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.rest.config.H2TestProfileJPAConfig;
import com.emu.apps.qcm.rest.controllers.ApiRestMappings;
import com.emu.apps.qcm.rest.controllers.management.resources.UploadResource;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {UploadRestControllerIntegrationTest.Initializer.class})
class UploadRestControllerIntegrationTest {

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

    @Test
    void shouldUploadFile() {

        var resource = new ClassPathResource("javaquestions2017.json");
        var map = new LinkedMultiValueMap <>();
        map.add("file", resource);
        map.add("fileType", MediaType.APPLICATION_JSON_VALUE);

        var postResponse = restTemplate
                .withBasicAuth(H2TestProfileJPAConfig.USERNAME_TEST, H2TestProfileJPAConfig.USER_PASSWORD)
                .exchange(getURL(ApiRestMappings.MANAGEMENT_API + ApiRestMappings.UPLOADS + "/json"), HttpMethod.POST, new HttpEntity <>(map), UploadResource.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();


//        final ResponseEntity <UploadDto> postResponse = testRestTemplate.exchange(getURL(QcmApi.API_V1 + "/upload/json"),
//                HttpMethod.POST,
//                new HttpEntity <>(map, getHeaders()),
//                UploadDto.class);

        ;

//        final ResponseEntity<PageQuestionnaireDto> responseGet = getRestTemplate().exchange(createURLWithPort(QcmApi.API_V1 + "/questionnaires/"), HttpMethod.GET,
//                new HttpEntity<>(null, authHeaders), PageQuestionnaireDto.class);
//
//        List<QuestionnaireDto> questionnaireDtos = responseGet.getBody().getContent();
//
//        assertThat(responseGet.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
//        assertThat(questionnaireDtos).isNotNull().isNotEmpty();
//        QuestionnaireDto first = Iterables.getFirst(questionnaireDtos, null);
//        assertThat(first).isNotNull();

    }

}

