package com.emu.apps.qcm.application.export.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "QuestionExport")
public class QuestionExport {

    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String questionText;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("category")
    private CategoryExport category;

    @JsonProperty("responses")
    private List <ResponseExport> responses;

    @JsonProperty("tags")
    private Set <QuestionTagExport> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("position")
    private Long position;

    @JsonProperty("points")
    private Long points;


}
