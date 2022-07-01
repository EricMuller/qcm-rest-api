package com.emu.apps.qcm.rest.controllers.domain.resources;

import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.UpdateQuestionnaire;
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
