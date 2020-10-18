package com.emu.apps.qcm.api.dtos.published;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"questionnaireUuid", "position","type","status", "category", "tags","question","responses","tip" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "PushishedQuestionnaireQuestion")
public class PushishedQuestionnaireQuestionDto {

    @JsonProperty("questionnaire_uuid")
    private String questionnaireUuid;

    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String questionText;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("category")
    private String category;

    @JsonProperty("responses")
    private List <PublishedResponseDto> responses;

    @JsonProperty("tags")
    private Set <String> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("position")
    private Long position;

}
