package com.emu.apps.qcm.spi.webmvc.rest.controllers;


import com.emu.apps.qcm.aggregates.Upload;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest(classes = {SpringBootJpaTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles(value = "webmvc")
public class UploadRestControllerTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldUploadFile() {

        ClassPathResource resource = new ClassPathResource("javaquestions2017.json");
        MultiValueMap <String, Object> map = new LinkedMultiValueMap <>();
        map.add("file", resource);
        map.add("fileType", MediaType.APPLICATION_JSON_VALUE);

        final ResponseEntity <Upload> postResponse = restTemplate
                .withBasicAuth(SpringBootJpaTestConfig.USER_TEST, SpringBootJpaTestConfig.USER_PASSWORD)
                .exchange(getURL(ApiRestMappings.PUBLIC_API + ApiRestMappings.UPLOADS + "/json"), HttpMethod.POST, new HttpEntity <>(map), Upload.class);
        assertThat(postResponse.getBody()).isNotNull();


//        final ResponseEntity <UploadDto> postResponse = testRestTemplate.exchange(getURL(QcmApi.API_V1 + "/upload/json"),
//                HttpMethod.POST,
//                new HttpEntity <>(map, getHeaders()),
//                UploadDto.class);

        assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

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

