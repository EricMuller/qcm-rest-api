package com.emu.apps.qcm.api.dtos.export.v1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "CategoryExport")
public class CategoryExportDto {

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("type")
    private String type;

}