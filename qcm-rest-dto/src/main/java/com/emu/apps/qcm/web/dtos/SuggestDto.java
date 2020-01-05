package com.emu.apps.qcm.web.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestDto {

    private String fieldName;

    private String libelle;

    private Long id;

}
