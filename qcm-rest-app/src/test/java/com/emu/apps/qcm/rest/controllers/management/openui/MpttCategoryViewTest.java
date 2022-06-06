package com.emu.apps.qcm.rest.controllers.management.openui;

import com.emu.apps.qcm.rest.controllers.management.resources.CategoryResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MpttCategoryViewTest {

    private final ObjectMapper MAPPER = new ObjectMapper();

    private FixtureResources fixtureResources = new FixtureResources();

    @Test
    void readValueWithCategoryViewUpdateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(CategoryView.Update.class)
                .writeValueAsString(fixtureResources.createCategory());

        var resources = MAPPER.readValue(valueAsString, CategoryResource.class);

        assertThat(resources).isNotNull();

        assertAll(
                () -> assertThat(resources.getUuid()).isNotNull(),
                () -> assertThat(resources.getLibelle()).isNotNull(),
                () -> assertThat(resources.getVersion()).isNotNull(),
                () -> assertThat(resources.getDateCreation()).isNull(),
                () -> assertThat(resources.getDateModification()).isNull());

    }

    @Test
    void readValueWithCategoryViewCreateTest() throws IOException {

        var valueAsString = MAPPER.writerWithView(CategoryView.Create.class)
                .writeValueAsString(fixtureResources.createCategory());

        var resources = MAPPER.readValue(valueAsString, CategoryResource.class);

        assertThat(resources).isNotNull();

        assertAll(
                () -> assertThat(resources.getUuid()).isNull(),
                () -> assertThat(resources.getLibelle()).isNotNull(),
                () -> assertThat(resources.getVersion()).isNull(),
                () -> assertThat(resources.getDateCreation()).isNull(),
                () -> assertThat(resources.getDateModification()).isNull());

    }

}
