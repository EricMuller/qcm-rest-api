package com.emu.apps.qcm.api.dtos.export.v1;


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
@JsonRootName(value = "QuestionExport")
public class QuestionExportDto {

    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String question;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("category")
    private CategoryExportDto category;

    @JsonProperty("responses")
    private List <ResponseExportDto> responses;

    @JsonProperty("tags")
    private Set <QuestionTagExportDto> questionTags;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("position")
    private Long position;

    @JsonProperty("points")
    private Long points;


}
