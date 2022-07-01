package com.emu.apps.qcm.rest.controllers.domain.resources;

import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.GetQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.UpdateQuestion;
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

    @JsonView({GetQuestion.class, UpdateQuestion.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({GetQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty("response")
    private String responseText;

    @JsonView({GetQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty("good")
    private Boolean good;

    @JsonView({GetQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    @JsonProperty(value = "number")
    private Long number;

}
