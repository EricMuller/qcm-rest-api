package com.emu.apps.qcm.rest.resources;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName(value = "QuestionnaireQuestion")
public class QuestionnaireQuestionResource {

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
    private CategoryResource category;

    @JsonProperty("responses")
    private List <ResponseResource> responses;

    @JsonProperty("tags")
    private Set <TagResource> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("status")
    private String status;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "points")
    private Integer points;


}
