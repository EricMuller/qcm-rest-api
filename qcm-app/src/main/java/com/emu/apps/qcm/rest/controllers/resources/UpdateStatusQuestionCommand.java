package com.emu.apps.qcm.rest.controllers.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "QuestionPatch")
public class UpdateStatusQuestionCommand {

    @JsonProperty("status")
    private String status;

}
