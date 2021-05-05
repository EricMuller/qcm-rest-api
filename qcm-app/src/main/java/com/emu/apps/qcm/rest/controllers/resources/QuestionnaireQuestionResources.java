package com.emu.apps.qcm.rest.controllers.resources;


import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.domain.model.base.DomainId;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestionResources {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;


    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String question;

    @JsonProperty("type")
    private String type;

    @JsonProperty("category")
    private CategoryResources category;

    @JsonProperty("responses")
    private List <ResponseResources> responses;

    @JsonProperty("tags")
    private Set <QuestionTagResources> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("status")
    private String status;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "points")
    private Integer points;


}
