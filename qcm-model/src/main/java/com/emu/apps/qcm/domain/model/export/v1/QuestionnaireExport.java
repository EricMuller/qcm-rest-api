package com.emu.apps.qcm.domain.model.export.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "QuestionnaireExport")
public class QuestionnaireExport {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private CategoryExport category;

    @JsonProperty("tags")
    private Set <QuestionnaireTagExport> questionnaireTags;

    @JsonProperty("status")
    private String status;

    @JsonProperty("website")
    private String website;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

}
