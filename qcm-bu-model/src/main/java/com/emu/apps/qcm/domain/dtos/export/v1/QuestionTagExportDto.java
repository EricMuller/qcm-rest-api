package com.emu.apps.qcm.domain.dtos.export.v1;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "QuestionTagExport")
public class QuestionTagExportDto {

    private String libelle;

}