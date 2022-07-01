package com.emu.apps.qcm.rest.controllers.services.search;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "QuestionTag")
public class QuestionTag {

    private String uuid;

    private String libelle;

}
