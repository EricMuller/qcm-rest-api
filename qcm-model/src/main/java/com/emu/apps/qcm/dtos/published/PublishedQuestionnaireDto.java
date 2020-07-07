package com.emu.apps.qcm.dtos.published;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedQuestionnaireDto {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("tags")
    private Set <String> questionnaireTags;

    @JsonProperty("status")
    private String status;

    @JsonProperty("website")
    private String website;

    @JsonProperty("published")
    private Boolean published;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private LocalDateTime dateCreation;

    @JsonProperty("dateModification")
    private LocalDateTime dateModification;

}