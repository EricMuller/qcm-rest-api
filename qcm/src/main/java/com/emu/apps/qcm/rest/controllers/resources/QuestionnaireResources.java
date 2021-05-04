package com.emu.apps.qcm.rest.controllers.resources;

import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.domain.model.base.DomainId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class QuestionnaireResources  {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private CategoryResources category;

    @JsonProperty("tags")
    private Set <QuestionnaireTagResources> questionnaireTags;

    @JsonProperty("status")
    private String status;

    @JsonProperty("website")
    private String website;

    @JsonProperty("published")
    private Boolean published;

    @JsonProperty("created_by")
    private String createdBy;

}
