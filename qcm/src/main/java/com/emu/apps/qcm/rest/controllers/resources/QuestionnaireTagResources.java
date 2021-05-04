package com.emu.apps.qcm.rest.controllers.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireTagResources {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("libelle")
    private String libelle;

}
