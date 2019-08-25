package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.H2TestProfileJPAConfig;
import com.emu.apps.qcm.web.rest.QcmApi;
import com.emu.apps.qcm.web.rest.dtos.PageDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class UploadRestControllerIntegrationTest extends SpringBootWebTestCase {


    @Test
    public void shouldUploadFile() {

        ClassPathResource resource = new ClassPathResource("javaquestions2017.json");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);

        MultiValueMap<String, String> authHeaders = new LinkedMultiValueMap();
        String token = new String(Base64.getEncoder().encode((H2TestProfileJPAConfig.USER_TEST + ":" + H2TestProfileJPAConfig.USER_PASSWORD).getBytes()));
        authHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + token);

        final ResponseEntity<String> response = getRestTemplate().exchange(createURLWithPort(QcmApi.API_V1 + "/upload/json"), HttpMethod.POST,
                new HttpEntity<>(map, authHeaders), String.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);


        final ResponseEntity<PageQuestionnaireDto> responseGet = getRestTemplate().exchange(createURLWithPort(QcmApi.API_V1 + "/questionnaires/"), HttpMethod.GET,
                new HttpEntity<>(null, authHeaders), PageQuestionnaireDto.class);

        List<QuestionnaireDto> questionnaireDtos = responseGet.getBody().getContent();

        assertThat(responseGet.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(questionnaireDtos).isNotNull().isNotEmpty();
        QuestionnaireDto first = Iterables.getFirst(questionnaireDtos, null);
        assertThat(first).isNotNull();


    }

    public static class PageQuestionnaireDto extends PageDto<QuestionnaireDto> {

    }
}

