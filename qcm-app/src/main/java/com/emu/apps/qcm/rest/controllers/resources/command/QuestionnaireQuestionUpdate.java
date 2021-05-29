package com.emu.apps.qcm.rest.controllers.resources.command;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestionUpdate {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "points")
    private Integer points;


}
