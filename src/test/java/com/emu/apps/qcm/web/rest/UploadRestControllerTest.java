package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.Application;
import com.emu.apps.qcm.web.rest.dtos.PageDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class UploadRestControllerTest {

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
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer FOO");
        headers.setContentType(MediaType.APPLICATION_JSON);

        ClassPathResource resource = new ClassPathResource("javaquestions2017.json", getClass());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);

        final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/upload/questionnaire"), HttpMethod.POST,
                                                                      new HttpEntity<>(map), String.class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/v1/questionnaires/"));
        final ResponseEntity<PageQuestionnaireDto> responseGet = restTemplate.getForEntity(builder.build().encode().toUri(), PageQuestionnaireDto.class);


        List<QuestionnaireDto> questionnaireDtos = responseGet.getBody().getContent();

        assertThat(responseGet.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(questionnaireDtos).isNotNull().isNotEmpty();
        QuestionnaireDto first = Iterables.getFirst(questionnaireDtos, null);
        assertThat(first).isNotNull();


    }

    public static class PageQuestionnaireDto extends PageDto<QuestionnaireDto> {

    }
}

