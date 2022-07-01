package com.emu.apps.qcm.rest.controllers.domain.resources;

import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.GetQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.UpdateQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "QuestionTag")
public class QuestionTagResource {

    @JsonProperty("uuid")
    @JsonView({GetQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({GetQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    private String libelle;

}
