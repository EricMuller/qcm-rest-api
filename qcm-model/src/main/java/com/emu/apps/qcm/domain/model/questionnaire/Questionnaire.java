package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.domain.model.base.DomainId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire extends DomainId {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("tags")
    private Set <QuestionnaireTag> questionnaireTags;

    @JsonProperty("status")
    private String status;

    @JsonProperty("website")
    private String website;

    @JsonProperty("published")
    private Boolean published;

    @JsonProperty("created_by")
    private String createdBy;

}
