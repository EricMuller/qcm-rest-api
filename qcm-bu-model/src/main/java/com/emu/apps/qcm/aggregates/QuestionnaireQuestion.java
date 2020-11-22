package com.emu.apps.qcm.aggregates;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestion extends Domain {

    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String question;

    @JsonProperty("type")
    private String type;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("responses")
    private List <Response> responses;

    @JsonProperty("tags")
    private Set <QuestionTag> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("status")
    private String status;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "points")
    private Integer points;


}
