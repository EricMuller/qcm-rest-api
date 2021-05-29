package com.emu.apps.qcm.rest.controllers.resources;

import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionView;
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
public class ResponseResources {

    @JsonView({QuestionView.Find.class, QuestionView.Update.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({QuestionView.Find.class, QuestionView.Update.class})
    @JsonProperty("version")
    private Long version;

    @JsonView({QuestionView.Find.class, QuestionView.Update.class, QuestionView.Create.class})
    @JsonProperty("response")
    private String responseText;

    @JsonView({QuestionView.Find.class, QuestionView.Update.class, QuestionView.Create.class})
    @JsonProperty("good")
    private Boolean good;

    @JsonView({QuestionView.Find.class, QuestionView.Update.class, QuestionView.Create.class})
    @JsonProperty(value = "number")
    private Long number;



}
