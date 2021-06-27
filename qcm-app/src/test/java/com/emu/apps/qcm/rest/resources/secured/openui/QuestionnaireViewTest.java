package com.emu.apps.qcm.rest.resources.secured.openui;

import com.emu.apps.qcm.rest.controllers.secured.openui.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.secured.resources.QuestionnaireResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class QuestionnaireViewTest {

    private final ObjectMapper MAPPER = new ObjectMapper();

    private FixtureResources fixtureResources = new FixtureResources();

    @Test
    void readValueWithQuestionnaireViewUpdateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(QuestionnaireView.Update.class)
                .writeValueAsString(fixtureResources.createQuestionnaire());

        var resources = MAPPER.readValue(valueAsString, QuestionnaireResource.class);

        assertThat(resources).isNotNull();

        assertThat(resources.getUuid()).isNotNull();
        assertThat(resources.getStatus()).isNotNull();
        assertThat(resources.getTitle()).isNotNull();
        assertThat(resources.getWebsite()).isNotNull();

        assertThat(resources.getCategory()).isNotNull();
        assertThat(resources.getCategory().getUuid()).isNotNull();
        assertThat(resources.getCategory().getLibelle()).isNull();
        assertThat(resources.getCategory().getDateCreation()).isNull();
        assertThat(resources.getCategory().getDateModification()).isNull();
        assertThat(resources.getCategory().getType()).isNull();
        assertThat(resources.getCategory().getUserId()).isNull();

        assertThat(resources.getTags()).isNotNull().size().isEqualTo(1);
        var tagResources = resources.getTags().stream().findFirst().get();
        assertThat(tagResources.getUuid()).isNotNull();
        assertThat(tagResources.getLibelle()).isNotNull();

    }

    @Test
    void readValueWithQuestionnaireViewCreateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(QuestionnaireView.Create.class)
                .writeValueAsString(fixtureResources.createQuestionnaire());

        var resources = MAPPER.readValue(valueAsString, QuestionnaireResource.class);

        assertThat(resources).isNotNull();

        assertThat(resources.getUuid()).isNull();
        assertThat(resources.getStatus()).isNotNull();
        assertThat(resources.getTitle()).isNotNull();
        assertThat(resources.getWebsite()).isNotNull();

        assertThat(resources.getCategory()).isNotNull();
        assertThat(resources.getCategory().getUuid()).isNotNull();
        assertThat(resources.getCategory().getLibelle()).isNull();
        assertThat(resources.getCategory().getDateCreation()).isNull();
        assertThat(resources.getCategory().getDateModification()).isNull();
        assertThat(resources.getCategory().getType()).isNull();
        assertThat(resources.getCategory().getUserId()).isNull();

        assertThat(resources.getTags()).isNotNull().size().isEqualTo(1);
        var tagResources = resources.getTags().stream().findFirst().get();
        assertThat(tagResources.getUuid()).isNotNull();
        assertThat(tagResources.getLibelle()).isNotNull();


    }


}
