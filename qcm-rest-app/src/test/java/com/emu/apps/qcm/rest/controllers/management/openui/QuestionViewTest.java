package com.emu.apps.qcm.rest.controllers.management.openui;

import com.emu.apps.qcm.rest.controllers.management.resources.QuestionResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QuestionViewTest {

    private final ObjectMapper MAPPER = new ObjectMapper();

    private FixtureResources fixtureResources = new FixtureResources();

    @Test
    void readValueWithQuestionViewUpdateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(QuestionView.Update.class)
                .writeValueAsString(fixtureResources.createQuestion());

        var resources = MAPPER.readValue(valueAsString, QuestionResource.class);

        assertThat(resources).isNotNull();

        assertAll(
                () -> assertThat(resources.getUuid()).isNotNull(),
                () -> assertThat(resources.getStatus()).isNotNull(),
                () -> assertThat(resources.getTip()).isNotNull(),
                () -> assertThat(resources.getType()).isNotNull(),
                () -> assertThat(resources.getQuestionText()).isNotNull(),
                () -> assertThat(resources.getResponses()).isNotNull().size().isEqualTo(1),
                () -> assertThat(resources.getVersion()).isNotNull(),
                () -> assertThat(resources.getDateCreation()).isNull(),
                () -> assertThat(resources.getDateModification()).isNull()
        );

        var responseResource = resources.getResponses().stream().findFirst().get();

        assertAll(
                () -> assertThat(responseResource.getUuid()).isNotNull(),
                () -> assertThat(responseResource.getResponseText()).isNotNull(),
                () -> assertThat(responseResource.getGood()).isNotNull(),
                () -> assertThat(responseResource.getNumber()).isNotNull(),
                () -> assertThat(responseResource.getVersion()).isNotNull());

        assertThat(resources.getTags()).isNotNull().size().isEqualTo(1);

        var tagResources = resources.getTags().stream().findFirst().get();

        assertAll(
                () -> assertThat(tagResources.getUuid()).isNotNull(),
                () -> assertThat(tagResources.getLibelle()).isNotNull());


    }

    @Test
    void readValueWithQuestionViewCreateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(QuestionView.Create.class)
                .writeValueAsString(fixtureResources.createQuestion());

        var resources = MAPPER.readValue(valueAsString, QuestionResource.class);

        assertThat(resources).isNotNull();

        assertAll(
                () -> assertThat(resources.getUuid()).isNull(),
                () -> assertThat(resources.getStatus()).isNotNull(),
                () -> assertThat(resources.getTip()).isNotNull(),
                () -> assertThat(resources.getType()).isNotNull(),
                () -> assertThat(resources.getQuestionText()).isNotNull(),
                () -> assertThat(resources.getResponses()).isNotNull().size().isEqualTo(1),
                () -> assertThat(resources.getVersion()).isNull(),
                () -> assertThat(resources.getDateCreation()).isNull(),
                () -> assertThat(resources.getDateModification()).isNull()
        );

        var responseResource = resources.getResponses().stream().findFirst().get();

        assertAll(
                () -> assertThat(responseResource.getUuid()).isNull(),
                () -> assertThat(responseResource.getResponseText()).isNotNull(),
                () -> assertThat(responseResource.getGood()).isNotNull(),
                () -> assertThat(responseResource.getNumber()).isNotNull(),
                () -> assertThat(responseResource.getVersion()).isNull(),
                () -> assertThat(resources.getTags()).isNotNull().size().isEqualTo(1)
        );
        var tagResources = resources.getTags().stream().findFirst().get();


        assertAll(
                () -> assertThat(tagResources.getUuid()).isNotNull(),
                () -> assertThat(tagResources.getLibelle()).isNotNull()
        );
    }

}
