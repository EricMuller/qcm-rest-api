package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.UpdateQuestionnaire;
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
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String libelle;

}
