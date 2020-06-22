package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.infrastructure.Fixture;
import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.models.CategoryDto;
import com.emu.apps.qcm.webmvc.rest.ApiRestMappings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.emu.apps.qcm.web.security.RestHeaders.headers;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {SpringBootTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class CategoryControllerTest {

    private static final String CATEGORY_URI = ApiRestMappings.PUBLIC_API + ApiRestMappings.CATEGORIES;

    private static final String LIBELLE = "cate 1";

    @LocalServerPort
    private int port;

    @Autowired
    private Fixture fixture;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private CategoryDto createCategoryDto() {

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setType(Type.QUESTION.name());
        categoryDto.setLibelle(LIBELLE);

        return categoryDto;
    }

    @BeforeEach
    public void setup() {
        fixture.emptyDatabase();
    }

    @Test
    public void postCategoryShouldBeOk() {

        // create a new category
        final ResponseEntity <CategoryDto> posteResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryDto.class);

        assertThat(posteResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(posteResponse.getBody().getUuid()).isNotNull();
        assertThat(posteResponse.getBody().getType()).isNotNull();
    }

    @Test
    public void getCategoryByIdShouldReturnCategory() {

        // create a new category
        final ResponseEntity <CategoryDto> postResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryDto.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        String uuid = postResponse.getBody().getUuid();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI + "/{uuid}"));
        URI uri = builder.build().expand(uuid).encode().toUri();

        final ResponseEntity <CategoryDto> getResponse = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity <>(headers()), CategoryDto.class);


        assertThat(getResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();

        assertThat(getResponse.getBody().getUuid()).isNotNull().isEqualTo(uuid);
        assertThat(getResponse.getBody().getType()).isNotNull().isEqualTo(Type.QUESTION.name());
        assertThat(getResponse.getBody().getLibelle()).isNotNull().isEqualTo(LIBELLE);

    }

    @Test
    public void getCategoryShouldReturnCategory() {

        final ResponseEntity <CategoryDto> postResponse = restTemplate
                .exchange(getURL(CATEGORY_URI), HttpMethod.POST,
                        new HttpEntity <>(createCategoryDto(),
                                headers()), CategoryDto.class);

        String uuid = postResponse.getBody().getUuid();

        URI uriQuestion = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI))
                .queryParam("type", Type.QUESTION.name()).build().toUri();

        final ResponseEntity <CategoryDto[]> getResponse = restTemplate
                .exchange(uriQuestion, HttpMethod.GET, new HttpEntity <>(headers()), CategoryDto[].class);

        assertThat(getResponse.getBody()).isNotEmpty();

        CategoryDto categoryDto = getResponse.getBody()[0];

        assertThat(categoryDto.getUuid()).isNotNull().isEqualTo(uuid);
        assertThat(categoryDto.getType()).isNotNull().isEqualTo(Type.QUESTION.name());
        assertThat(categoryDto.getLibelle()).isNotNull().isEqualTo(LIBELLE);

        URI uriQuestionnaire = UriComponentsBuilder.fromHttpUrl(getURL(CATEGORY_URI))
                .queryParam("type", Type.QUESTIONNAIRE.name()).build().toUri();

        final ResponseEntity <CategoryDto[]> getResponse2 = restTemplate
                .exchange(uriQuestionnaire, HttpMethod.GET, new HttpEntity <>(headers()), CategoryDto[].class);

        assertThat(getResponse2.getBody()).isEmpty();

    }


}

