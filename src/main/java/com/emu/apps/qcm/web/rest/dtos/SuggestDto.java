package com.emu.apps.qcm.web.rest.dtos;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "Suggest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestDto {

    private String fieldName;

    private String libelle;

    private Long id;

}
