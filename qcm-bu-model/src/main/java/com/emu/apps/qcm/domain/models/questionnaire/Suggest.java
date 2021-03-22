package com.emu.apps.qcm.domain.models.questionnaire;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suggest {

    private String fieldName;

    private String libelle;

    private Long id;

}
