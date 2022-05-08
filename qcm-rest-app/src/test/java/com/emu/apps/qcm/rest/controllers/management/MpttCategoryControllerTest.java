package com.emu.apps.qcm.rest.controllers.management;


import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.repositories.DbRepositoryFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import com.emu.apps.qcm.rest.controllers.ApiRestMappings;
import com.emu.apps.qcm.rest.controllers.management.resources.CategoryResource;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.emu.apps.qcm.rest.config.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {MpttCategoryControllerTest.Initializer.class})
class MpttCategoryControllerTest {

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


    private static final String CATEGORY_URI = ApiRestMappings.MANAGEMENT_API + ApiRestMappings.CATEGORIES;

    private static final String LIBELLE = "cate 1";

    @LocalServerPort
    private int port;

    @Autowired
    private DbFixture fixture;

    @Autowired
    private DbRepositoryFixture dbRepositoryFixture;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private MpttCategory createCategoryDto() {


        MpttCategory mpttCategory = new MpttCategory();

        mpttCategory.setType(MpttType.QUESTION.name());
        mpttCategory.setLibelle(LIBELLE);

        return mpttCategory;
    }

    @BeforeEach
    public void setup() {
        fixture.emptyDatabase();
        dbRepositoryFixture.createAccountTest();
    }

    @Test
    void postCategoryShouldBeOk() {

        // create a new category
        final ResponseEntity <CategoryResource> posteResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryResource.class);

        assertThat(posteResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(posteResponse.getBody().getUuid()).isNotNull();
        assertThat(posteResponse.getBody().getType()).isNotNull();
    }

    @Test
    void getCategoryByIdShouldReturnCategory() {

        // create a new category
        final ResponseEntity <CategoryResource> postResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryResource.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        assertThat(postResponse.getBody().getUuid()).isNotNull();
        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <CategoryResource> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), CategoryResource.class);


        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();

        assertThat(getResponse.getBody().getUuid()).isNotNull().isEqualTo(uuid);
        assertThat(getResponse.getBody().getType()).isNotNull().isEqualTo(MpttType.QUESTION.name());
        assertThat(getResponse.getBody().getLibelle()).isNotNull().isEqualTo(LIBELLE);

    }

    @Test
    void getCategoryShouldReturnCategory() {

        final ResponseEntity <CategoryResource> postResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryResource.class);

        String uuid = postResponse.getBody().getUuid();

        URI uriQuestion = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI))
                .queryParam("type", MpttType.QUESTION.name()).build().toUri();

        final ResponseEntity <CategoryResource[]> getResponse = restTemplate
                .exchange(uriQuestion, HttpMethod.GET, new HttpEntity <>(headers()), CategoryResource[].class);

        assertThat(getResponse.getBody()).isNotEmpty();

        CategoryResource category = getResponse.getBody()[0];

        assertThat(category.getUuid()).isNotNull().isEqualTo(uuid);
        assertThat(category.getType()).isNotNull().isEqualTo(MpttType.QUESTION.name());
        assertThat(category.getLibelle()).isNotNull().isEqualTo(LIBELLE);

        URI uriQuestionnaire = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI))
                .queryParam("type", MpttType.QUESTIONNAIRE.name()).build().toUri();

        final ResponseEntity <CategoryResource[]> getResponse2 = restTemplate
                .exchange(uriQuestionnaire, HttpMethod.GET, new HttpEntity <>(headers()), CategoryResource[].class);

        assertThat(getResponse2.getBody()).isEmpty();

    }


}

