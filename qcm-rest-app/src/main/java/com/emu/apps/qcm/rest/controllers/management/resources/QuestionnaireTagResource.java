package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
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
@JsonRootName(value = "QuestionnaireTag")
public class QuestionnaireTagResource {

    @JsonProperty("uuid")
    @JsonView({QuestionnaireView.Find.class, QuestionnaireView.Update.class, QuestionnaireView.Create.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({QuestionnaireView.Find.class, QuestionnaireView.Update.class, QuestionnaireView.Create.class})
    private String libelle;

}
