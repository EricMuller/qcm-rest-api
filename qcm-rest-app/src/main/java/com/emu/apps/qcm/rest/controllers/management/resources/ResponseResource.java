package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.FindQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.UpdateQuestion;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@JsonRootName(value = "Response")
public class ResponseResource {

    @JsonView({FindQuestion.class, UpdateQuestion.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({FindQuestion.class, UpdateQuestion.class})
    @JsonProperty("version")
    private Long version;

    @JsonView({FindQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty("response")
    private String responseText;

    @JsonView({FindQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty("good")
    private Boolean good;

    @JsonView({FindQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty(value = "number")
    private Long number;



}
