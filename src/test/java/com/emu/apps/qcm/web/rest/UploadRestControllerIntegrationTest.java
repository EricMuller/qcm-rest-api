package com.emu.apps.qcm.web.rest;

import com.emu.apps.Application;
import com.emu.apps.ApplicationTest;
import com.emu.apps.qcm.web.rest.dtos.PageDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.security.WebSecurityTestConfig;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = {Application.class, WebSecurityTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("http://localhost:${local.server.port}")
    private String host;

    private RestOperations restTemplate = new TestRestTemplate().getRestTemplate();

    public String getHost() {
        return host;
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void name() {
    }

    @Test
    public void shouldUploadFile() {

        ClassPathResource resource = new ClassPathResource("javaquestions2017.json", getClass());
        MultiValueMap <String, Object> map = new LinkedMultiValueMap <>();
        map.add("file", resource);

        MultiValueMap <String, String> authHeaders = new LinkedMultiValueMap();
        String token = new String(Base64.getEncoder().encode((ApplicationTest.USER_TEST + ":" + ApplicationTest.USER_PASSWORD).getBytes()));
        authHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + token);

        final ResponseEntity <String> response = restTemplate.exchange(createURLWithPort(QcmApi.API_V1 + "/upload/questionnaire"), HttpMethod.POST,
                new HttpEntity <>(map, authHeaders), String.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);


        final ResponseEntity <PageQuestionnaireDto> responseGet = restTemplate.exchange(createURLWithPort(QcmApi.API_V1 + "/questionnaires/"), HttpMethod.GET,
                new HttpEntity <>(null, authHeaders), PageQuestionnaireDto.class);

        List <QuestionnaireDto> questionnaireDtos = responseGet.getBody().getContent();

        assertThat(responseGet.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(questionnaireDtos).isNotNull().isNotEmpty();
        QuestionnaireDto first = Iterables.getFirst(questionnaireDtos, null);
        assertThat(first).isNotNull();


    }

    public static class PageQuestionnaireDto extends PageDto <QuestionnaireDto> {

    }
}

