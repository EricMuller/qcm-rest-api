package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.FindQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.UpdateQuestion;
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
    @JsonView({FindQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({FindQuestion.class, UpdateQuestion.class, CreateQuestion.class})
    private String libelle;

}
