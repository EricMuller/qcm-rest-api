package com.emu.apps.qcm.domain.dtos.export.v1;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "QuestionnaireTagExport")
public class QuestionnaireTagExportDto {

    private String libelle;


}
