package com.emu.apps.qcm.application.export.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "QuestionnaireTagExport")
public class QuestionnaireTagExport {

    private String libelle;


}
